# 🧭 1. Ordem de desenvolvimento (Angular + exploração controlada)

# 1. Setup base e entendimento do Angular

* Confirmar projeto rodando (`ng serve`)
* Validar Tailwind funcionando
* Validar routing ativo
* Entender estrutura `standalone`

Objetivo:

* garantir ambiente estável antes de qualquer feature

---

# 2. Estrutura arquitetural (não codar features ainda)

* Criar pastas base:

  * core
  * features
  * shared

* Criar `app.routes.ts`

* Criar layout base da aplicação:

  * shell simples
  * header básico
  * container de páginas

Objetivo:

* evitar começar direto em tela isolada

---

# 3. Primeira feature simples (UI pura)

* Criar feature `orders`
* Criar página estática:

  * Order Create (sem API ainda)
* Criar formulário visual:

  * produto
  * quantidade
  * botão

Objetivo:

* aprender Angular sem backend

---

# 4. Introdução de state local

* usar signals ou state simples
* capturar formulário
* simular submit

Objetivo:

* entender fluxo Angular sem HTTP

---

# 5. Primeira integração HTTP (simples)

* criar `OrderService`
* mock ou endpoint real (se já existir)
* implementar POST simples

Objetivo:

* entender HttpClient

---

# 6. Segunda página (leitura de dados)

* criar `OrderDashboard`
* GET /orders/:id
* exibir status simples

Objetivo:

* ciclo completo CRUD mínimo

---

# 7. Introdução de polling

* atualizar status automaticamente
* a cada 2–3s
* substituir manual refresh

Objetivo:

* entrar em reatividade real

---

# 8. Separação correta de responsabilidades

* mover lógica para services
* evitar lógica no component
* padronizar models

Objetivo:

* estabilizar arquitetura Angular

---

# 9. Tempo real (conceito avançado)

* preparar WebSocket/SSE (mesmo que backend ainda não esteja pronto)
* deixar estrutura pronta

Objetivo:

* entender arquitetura reativa

---

# 10. Dashboard simples

* página de monitoramento
* dados mock ou agregados

Objetivo:

* consolidar visão de app completo

---

# 🧱 2. Estrutura padrão do projeto (fixa)

Essa estrutura você NÃO deve quebrar durante o aprendizado.

```text
src/app
│
├── core
│   ├── services
│   │   ├── http
│   │   ├── order.service.ts
│   │   └── ...
│   │
│   ├── models
│   │   ├── order.model.ts
│   │   ├── order-status.model.ts
│   │   └── ...
│   │
│   ├── interceptors
│   └── config
│
│
├── features
│   │
│   └── orders
│       ├── pages
│       │   ├── create-order
│       │   └── order-dashboard
│       │
│       ├── components
│       │   └── order-status
│       │
│       ├── services
│       └── models
│
│
├── shared
│   ├── components
│   ├── directives
│   ├── pipes
│   └── ui
│
│
├── app.routes.ts
└── app.component.ts
```

---

# 🧠 Regras estruturais (importante manter)

## 🔹 Dependência permitida

```text
Component → Service → HttpClient → API
```

---

## 🔹 Proibido

* Component chamar HTTP direto
* Feature acessar outra feature diretamente
* Shared conter regra de negócio
* Models misturados com UI

---

## 🔹 Organização mental

* `core` = infraestrutura global
* `features` = domínio do sistema
* `shared` = reuso visual

---

## 🔹 Regra de evolução

Você só adiciona complexidade quando:

* fluxo anterior está funcionando
* não há gambiarras temporárias
* componente está isolado corretamente