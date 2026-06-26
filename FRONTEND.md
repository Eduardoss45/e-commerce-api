# 🖥️ Frontend — Plataforma de Pedidos com Angular

## Objetivo

Criar uma interface web para consumir o Order Service e acompanhar o processamento assíncrono dos pedidos em tempo real.

O frontend será responsável por:

- criar pedidos
- visualizar status
- acompanhar a saga de processamento
- receber atualizações via WebSocket/SSE
- exibir métricas simples do fluxo

O frontend **não conhece RabbitMQ**.

---

# 🧱 Arquitetura

```text
Angular
   |
   |
 HTTP
   |
   v
Order Service
   |
   |
 Eventos internos
   |
   v

RabbitMQ

   |
   v

Order Service
   |
   |
 WebSocket/SSE
   |
   v

Angular Dashboard
```

O Angular só conversa com o backend.

---

# 📦 Stack

## Framework

Angular

## Linguagem

TypeScript

## Comunicação

HTTP:

```
HttpClient
```

Tempo real:

```
WebSocket
ou
Server Sent Events (SSE)
```

## UI

Escolha simples:

- Angular Material

ou

- Tailwind CSS

---

# 🗂️ Estrutura inicial

Use Angular standalone components.

Estrutura:

```
src/app

├── core
│   ├── services
│   └── models
│
├── features
│
│   └── orders
│       ├── pages
│       │    ├── create-order
│       │    └── order-dashboard
│       │
│       ├── components
│       │    └── order-status
│       │
│       ├── services
│       │    └── order.service.ts
│       │
│       └── models
│            └── order.model.ts
│
├── shared
│
└── app.routes.ts
```

---

# 🧠 Conceitos Angular necessários

Antes de codar entenda:

## Component

Responsável pela tela.

Exemplo:

```
OrderDashboardComponent
```

Pensa como um componente React.

---

## Service

Responsável por lógica externa.

Exemplo:

```
OrderService
```

Ele chama API.

Equivalente:

React:

```
api.js
```

Angular:

```
service.ts
```

---

## Dependency Injection

Angular injeta serviços.

Exemplo mental:

```
Component
     |
     v
OrderService
     |
     v
HTTP
```

---

# 🔄 Fluxo criar pedido

Usuário:

```
Preenche formulário
```

↓

Angular:

```
OrderCreateComponent
```

↓

Chama:

```
OrderService.createOrder()
```

↓

HTTP:

```
POST /orders
```

↓

Backend:

```
order.created
```

↓

Resposta:

```json
{
  "orderId": "123",
  "status": "CREATED"
}
```

↓

Frontend salva:

```
pedido atual = 123
```

---

# 📄 Modelo de dados

Criar:

```
order.model.ts
```

Exemplo:

```typescript
export interface Order {
  orderId: string;

  status: 'CREATED' | 'COMPLETED' | 'CANCELLED';
}
```

---

# 🌐 Serviço HTTP

Criar:

```
order.service.ts
```

Responsabilidade:

- chamar API
- converter resposta

Não colocar lógica de tela aqui.

---

Fluxo:

```
Component

    ↓

Service

    ↓

HttpClient

    ↓

Spring API
```

---

# 📡 Tempo real

Objetivo:

Quando o backend fizer:

```
order.completed
```

o Angular recebe:

```json
{
  "orderId": "123",
  "status": "COMPLETED"
}
```

e atualiza a tela.

---

## Primeira implementação

Não comece com WebSocket.

Faça:

```
Polling
```

Primeiro.

Exemplo:

```
a cada 2 segundos

GET /orders/{id}
```

Quando funcionar:

troca para:

```
WebSocket/SSE
```

---

# 🔥 Implementação WebSocket futura

Fluxo:

Backend:

```
Order Service

quando status muda:

emit(event)
```

↓

WebSocket:

```
/topic/orders
```

↓

Angular:

```
subscribe()
```

↓

Atualiza:

```
OrderStatusComponent
```

---

# 🖥️ Telas

## 1. Criar Pedido

Rota:

```
/orders/new
```

Component:

```
CreateOrderComponent
```

Campos:

```
Produto
Quantidade
Cartão
CVV
```

Ação:

```
Criar
```

---

## 2. Dashboard

Rota:

```
/orders/:id
```

Mostra:

```
Pedido:

123


Status:

CREATED


Linha do tempo:

✓ Pedido criado

✓ Estoque reservado

✓ Pagamento

✓ Finalizado
```

---

# 📊 Dashboard de testes

Depois:

```
/monitor
```

Mostrar:

```
Pedidos processados:

1000


Sucesso:

920


Falha:

80


Tempo médio:

2.5s
```

---

# 🧪 Testes

Primeiro teste:

Criar pedido.

Esperado:

```
Angular
 |
POST
 |
Order Service
 |
RabbitMQ
 |
Order completed
 |
Angular atualiza
```

---

# Ordem de desenvolvimento

## Fase 1 — Angular básico

Aprender:

- componentes
- rotas
- services
- HttpClient

Implementar:

```
Criar pedido
```

---

## Fase 2 — Visualizar status

Implementar:

```
GET /orders/{id}
```

Mostrar:

```
CREATED
COMPLETED
CANCELLED
```

---

## Fase 3 — Tempo real

Adicionar:

```
SSE/WebSocket
```

Eliminar polling.

---

## Fase 4 — Simulação de carga

Criar:

```
Load Generator
```

Enviar:

```
1000 pedidos
```

Mostrar:

```
taxa sucesso
falhas
tempo
```

---

# O que NÃO fazer agora

Evitar:

❌ Redux/NgRx
❌ Microfrontend
❌ autenticação
❌ design complexo
❌ dezenas de componentes abstratos

O objetivo é aprender Angular e integrar com sua arquitetura de eventos.

---

# Resultado esperado do projeto

No final:

```
Usuário cria pedido no Angular

        ↓

Spring recebe

        ↓

Eventos passam pelo RabbitMQ

        ↓

Saga executa

        ↓

Angular recebe atualização em tempo real

        ↓

Dashboard mostra resultado
```
