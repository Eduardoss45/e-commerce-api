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
