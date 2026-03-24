# Sequencia Recomendada de Desenvolvimento

Esta sequencia segue o material do projeto e prioriza aprendizagem, fluxo de eventos e baixo acoplamento.

## 1. Fundacao e Infra
- Definir eventos e responsabilidades de cada servico em texto antes de codar.
- Definir o modelo basico de evento (eventId, type, timestamp, payload).
- Criar os projetos Spring Boot base (gateway + 4 servicos) sem logica de negocio.

## 2. Fase 1 - Fluxo Minimo (Order -> Inventory)
- Implementar Order Service com endpoint POST /orders.
- Persistir pedido (em memoria ou banco local simples).
- Publicar evento order.created.
- Implementar Inventory Service consumindo order.created.
- Reservar estoque (mock) e publicar inventory.reserved.
- Validar fluxo ponta a ponta no broker.

## 3. Fase 2 - Pipeline Completo
- Implementar Payment Service consumindo inventory.reserved.
- Processar pagamento (mock) e publicar payment.processed.
- Implementar Notification Service consumindo payment.processed.
- Registrar notificacao (log/email simulado).

## 4. Fase 3 - Resiliencia
- Implementar retry nos consumers.
- Configurar Dead Letter Queue (DLQ).
- Implementar idempotencia basica (por eventId).

## 5. Fase 4 - Observabilidade (Opcional)
- Padronizar logs estruturados.
- Medir tempos de processamento simples.
- Criar endpoints de health/metrics se fizer sentido.

## 6. Refinos e Extensoes
- Persistencia por servico (PostgreSQL por dominio).
- Saga/compensacao quando houver falha de pagamento.
- API Gateway com filtros (auth/log) se necessario.

## Checklist Rapido por Servico
- Evento que consome?
- Acao executada?
- Evento que publica?
- O que acontece se falhar?
