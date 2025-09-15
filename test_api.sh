#!/bin/bash

echo "=== TESTANDO API TODO LIST ==="
echo ""

BASE_URL="http://localhost:8080/api"

echo "1. Registrando um usuário ADMIN..."
ADMIN_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "email": "admin@test.com",
    "password": "123456",
    "role": "ADMIN"
  }')

echo "Resposta: $ADMIN_RESPONSE"
ADMIN_TOKEN=$(echo $ADMIN_RESPONSE | jq -r '.token')
echo "Token Admin: $ADMIN_TOKEN"
echo ""

echo "2. Registrando um usuário USER..."
USER_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user1",
    "email": "user1@test.com",
    "password": "123456",
    "role": "USER"
  }')

echo "Resposta: $USER_RESPONSE"
USER_TOKEN=$(echo $USER_RESPONSE | jq -r '.token')
echo "Token User: $USER_TOKEN"
echo ""

echo "3. Testando login do usuário USER..."
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user1",
    "password": "123456"
  }')
echo "Login resposta: $LOGIN_RESPONSE"
echo ""

echo "4. Criando um TODO com o usuário USER..."
TODO_RESPONSE=$(curl -s -X POST "$BASE_URL/todos" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $USER_TOKEN" \
  -d '{
    "title": "Minha primeira tarefa",
    "description": "Descrição da primeira tarefa",
    "completed": false
  }')
echo "TODO criado: $TODO_RESPONSE"
TODO_ID=$(echo $TODO_RESPONSE | jq -r '.id')
echo ""

echo "5. Listando TODOs do usuário..."
TODOS_LIST=$(curl -s -X GET "$BASE_URL/todos" \
  -H "Authorization: Bearer $USER_TOKEN")
echo "Lista de TODOs: $TODOS_LIST"
echo ""

echo "6. Testando acesso ADMIN - listando usuários..."
USERS_LIST=$(curl -s -X GET "$BASE_URL/users" \
  -H "Authorization: Bearer $ADMIN_TOKEN")
echo "Lista de usuários (apenas ADMIN): $USERS_LIST"
echo ""

echo "7. Testando acesso negado - USER tentando acessar usuários..."
FORBIDDEN_RESPONSE=$(curl -s -X GET "$BASE_URL/users" \
  -H "Authorization: Bearer $USER_TOKEN")
echo "Resposta de acesso negado: $FORBIDDEN_RESPONSE"
echo ""

echo "8. Atualizando o TODO..."
UPDATE_RESPONSE=$(curl -s -X PUT "$BASE_URL/todos/$TODO_ID" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $USER_TOKEN" \
  -d '{
    "title": "Tarefa atualizada",
    "description": "Descrição atualizada",
    "completed": true
  }')
echo "TODO atualizado: $UPDATE_RESPONSE"
echo ""

echo "=== TESTES CONCLUÍDOS ==="