# 🧱 Serviço de Estoque

Este microsserviço faz parte do **sistema de gerenciamento de estoque e monitoramento corporativo**, desenvolvido com foco em **arquitetura distribuída, segurança e boas práticas de integração**.

O serviço de **estoque** é responsável por gerenciar produtos, entradas e saídas, garantindo consistência e rastreabilidade dos dados. Ele se comunica com o serviço de **monitoramento de logs** através do **Apache Kafka**, enviando eventos para acompanhamento em tempo real.

---

## 🚀 Tecnologias Utilizadas

- **Java 21** — linguagem principal  
- **Spring Boot 3** — estrutura base do microsserviço  
- **Spring Data MongoDB** — persistência e consultas no banco NoSQL  
- **Apache Kafka** — mensageria para integração com outros serviços  
- **Jakarta Validation** — validação de dados de entrada  
- **Lombok** — redução de boilerplate no código  
- **Variáveis de Ambiente (.env)** — configuração segura de credenciais e URIs  

---

## 🧩 Arquitetura e Comunicação

O serviço segue uma arquitetura **baseada em microsserviços**, com comunicação assíncrona via Kafka.  
Cada evento importante (como movimentação de produto ou atualização de estoque) é enviado ao tópico configurado, sendo consumido pelo serviço de **monitoramento**.

---

📡 **Comunicação entre serviços:**

Estoque --> Kafka --> Monitoramento de Logs

---

## 🔐 Segurança e Boas Práticas

- Nenhum dado sensível é armazenado no código-fonte.  
- As credenciais e URIs do MongoDB e Kafka são passadas via variáveis de ambiente.  
- Logs seguem um padrão definido para rastreabilidade e auditoria.

---

## 📎 Serviço Relacionado

➡️ Confira também o repositório do serviço complementar de **monitoramento de logs**, responsável por consumir e registrar os eventos do sistema:  
👉 [andrewsec5/monitoramento-estoque](https://github.com/andrewsec5/monitoramento-estoque)

---

## 🧠 Autor

Desenvolvido por **Andrew**.  
📬 Feedbacks e sugestões são sempre bem-vindos!
