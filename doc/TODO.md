# 🛒 Plataforma de Pedidos — Event-Driven com Spring Boot

Sistema de pedidos de e-commerce baseado em **arquitetura orientada a eventos**, utilizando **Spring Boot** e mensageria para comunicação assíncrona entre serviços.

O objetivo é simular um fluxo real de processamento de pedidos com **consistência eventual**, **resiliência** e **baixo acoplamento**.

---

# 🎯 Objetivo do Projeto

Construir um sistema distribuído onde:

* serviços não se comunicam diretamente via HTTP
* toda comunicação ocorre via eventos
* cada serviço possui responsabilidade isolada

---

# 🧱 Visão Arquitetural

```text
Client
  │
  ▼
Order Service (API REST)
  │
  ▼
Event Broker (RabbitMQ ou Kafka)
  │
  ├── Inventory Service
  ├── Payment Service
  └── Notification Service
```

---

# 🔄 Fluxo de Eventos

```text
order.created
   ↓
inventory.reserved
   ↓
payment.processed
   ↓
order.completed
```

---

# 📦 Serviços

## 1. Order Service

Responsável por:

* criar pedidos
* publicar evento `order.created`
* atualizar status do pedido

---

## 2. Inventory Service

Responsável por:

* validar estoque
* reservar itens
* publicar `inventory.reserved`

---

## 3. Payment Service

Responsável por:

* processar pagamento
* publicar `payment.processed`

---

## 4. Notification Service

Responsável por:

* consumir eventos
* notificar usuário (log/email simulado)

---

# 🧠 Conceitos que você deve dominar

Este projeto NÃO é sobre código — é sobre arquitetura.

---

## Event-Driven

* comunicação assíncrona
* desacoplamento entre serviços

---

## Consistência eventual

* dados não são atualizados instantaneamente
* sistema converge ao estado final

---

## Idempotência

* consumir o mesmo evento múltiplas vezes **não pode quebrar o sistema**

---

## Retry + Dead Letter Queue

* falhas devem ser reprocessadas
* eventos inválidos vão para DLQ

---

# ⚙️ Stack

* Java + Spring Boot
* Spring AMQP (RabbitMQ) **ou** Spring Kafka
* Docker + docker-compose

---

# 🐳 Infraestrutura mínima

```yaml
RabbitMQ
PostgreSQL (opcional por serviço)
```

---

# 🧪 Estratégia de aprendizado (anti “copia e cola”)

Aqui está o ponto mais importante.

---

## 🔥 Regra principal

> Você NÃO pode implementar um serviço sem antes escrever o fluxo dele em texto.

---

## ✔️ Passo obrigatório antes de codar

Para cada serviço, responda:

```text
1. Que evento ele consome?
2. O que ele faz com esse evento?
3. Que evento ele publica?
4. O que acontece se falhar?
```

---

## Exemplo (Inventory)

```text
Consome: order.created
Ação: verifica estoque e reserva
Publica: inventory.reserved

Falha:
- sem estoque → inventory.failed
- erro técnico → retry
```

---

👉 Só depois disso você codifica.

---

# 🧪 Estratégia de testes (alinhada com seu perfil)

Sem complicar.

---

## Você deve testar:

### ✔️ 1. Publisher

* evento foi publicado corretamente

---

### ✔️ 2. Consumer

* evento recebido
* lógica executada

---

### ✔️ 3. Idempotência

* evento duplicado não quebra o sistema

---

## ❗ Não precisa agora

* cobertura alta
* mocks complexos

---

# 🔁 Fases de implementação

---

## Fase 1 — Base funcional

* Order Service publica `order.created`
* Inventory consome

👉 validar fluxo simples

---

## Fase 2 — Pipeline completo

* adicionar Payment
* adicionar Notification

---

## Fase 3 — Resiliência

* retry
* DLQ
* idempotência

---

## Fase 4 — Observabilidade (opcional forte)

* logs estruturados
* métricas básicas

---

# ⚠️ Regras de implementação

---

## ❌ Evite

* chamadas HTTP entre serviços
* lógica acoplada
* compartilhar banco entre serviços

---

## ✔️ Priorize

* eventos claros
* responsabilidades isoladas
* simplicidade

---

# 📊 Modelo de Evento (exemplo)

```json
{
  "eventId": "uuid",
  "type": "order.created",
  "timestamp": "2026-01-01T10:00:00Z",
  "payload": {
    "orderId": 1,
    "items": [
      { "productId": 10, "quantity": 2 }
    ]
  }
}
```

---

# 🧠 Decisões importantes

---

## RabbitMQ vs Kafka

* RabbitMQ → mais simples (recomendado para este projeto)
* Kafka → mais robusto (mais complexo)

👉 Para aprendizado: **RabbitMQ**

---

# 📈 O que esse projeto prova

Se bem feito, você demonstra:

* domínio de arquitetura distribuída
* entendimento de consistência
* desacoplamento real
* resiliência

---

# 🚀 Extensões futuras

* saga pattern (compensação)
* persistência por serviço
* API Gateway
* autenticação centralizada

---

# 🧾 Conclusão

Esse projeto não é sobre “fazer funcionar”.

É sobre provar que você entende:

* como sistemas reais funcionam
* como falhas são tratadas
* como serviços se comunicam sem acoplamento

---

## 📌 Próximo passo

Se quiser evoluir corretamente, posso te ajudar a montar:

> **estrutura inicial de um dos serviços (sem código pronto)**
> apenas com:

* responsabilidades
* classes necessárias
* fluxo interno

isso força aprendizado sem cair no “copiar e colar”.
