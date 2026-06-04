# 🧱 Visão geral (Gateway + Services)

```text
Client
  │
  ▼
API Gateway
  │
  ├── order-service
  ├── inventory-service
  ├── payment-service
  └── notification-service
          │
          ▼
      Event Broker (RabbitMQ)
```

---

# 🚪 API Gateway (GT)

## 🎯 Papel

- ponto único de entrada
- roteamento HTTP → services
- (opcional) autenticação / rate limit

---

## 🧩 Estrutura mínima

```text
gateway
 ├── config
 └── routes
```

---

## ✔️ Composição essencial

```text
- Spring Cloud Gateway
- Route config (paths → services)
- (opcional) filter (auth/log)
```

---

## 🔄 Exemplo mental de fluxo

```text
POST /orders
   ↓
Gateway
   ↓
order-service
```

---

# ⚙️ Service (SV) — padrão único para TODOS

Todos os serviços seguem **a mesma base**.

---

## 🧩 Estrutura padrão

```text
service-name
 ├── controller (se expõe HTTP)
 ├── service
 ├── domain
 ├── repository
 ├── messaging
 ├── dto
 └── config
```

---

## 🔑 Composição essencial

```text
- Spring Boot
- Banco próprio (opcional no início)
- Producer (publica evento)
- Consumer (escuta evento)
```

---

# 🔄 Padrão de comunicação

---

## Entrada

```text
HTTP → Controller → Service
```

---

## Saída

```text
Service → EventPublisher → Broker
```

---

## Consumo

```text
Broker → Consumer → Service
```

---

# 📦 Exemplo simplificado por serviço

---

## 🛒 order-service

```text
HTTP: POST /orders
↓
salva pedido
↓
publica: order.created
```

---

## 📦 inventory-service

```text
consome: order.created
↓
reserva estoque
↓
publica: inventory.reserved
```

---

## 💳 payment-service

```text
consome: inventory.reserved
↓
processa pagamento
↓
publica: payment.processed
```

---

## 🔔 notification-service

```text
consome: payment.processed
↓
notifica usuário
```

---

# 🔁 Fluxo completo (resumo)

```text
Client
 ↓
Gateway
 ↓
Order Service
 ↓ (event)
Broker
 ↓
Inventory
 ↓
Payment
 ↓
Notification
```

---

# 🧠 Regra de ouro

```text
Services NÃO se chamam via HTTP
→ só via eventos
```

---

# 📌 Essência (o que você precisa garantir)

---

## Gateway

- roteia corretamente
- não tem regra de negócio

---

## Service

- faz UMA responsabilidade
- publica evento
- consome evento
- não depende diretamente de outro service

---

# ⚠️ Simplificação intencional

Você NÃO precisa agora:

- auth centralizado
- service discovery
- config server
- saga completa

---

# 🎯 Se isso estiver claro, você já pode começar

Esse esquema já é suficiente para:

- montar docker-compose
- subir RabbitMQ
- criar serviços isolados
- implementar fluxo real
