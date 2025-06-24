# Trabalho 2 – Desenvolvimento Mobile  
## Harry Potter API

O objetivo deste trabalho é consultar a [HP-API](https://hp-api.onrender.com/) para demonstrar os conhecimentos adquiridos em corrotinas e web services.

Uma rápida utilização da interface web da API permite que o desenvolvedor entenda como consumir os endpoints oferecidos.

---

## 📱 Requisitos do Aplicativo

### 🧭 Activity Principal - Dashboard

A activity principal deve apresentar um **painel de botões** com as seguintes funcionalidades:

- **Listar um personagem específico (por ID)**
- **Listar os professores da escola**
- **Listar os estudantes de uma casa**
- **Ver Feitiços**
- **Sair (fechar o aplicativo)**

Os botões devem estar bem distribuídos na interface.

---

### 🧍 Activity: Listar um Personagem Específico

- Deve permitir ao usuário informar um **ID** para a busca.
- Utilizar o endpoint de busca por ID da API.
- Exibir os seguintes campos em um `TextView`:
  - `name`
  - `species`
  - `house`
  - `image` (foto do personagem)

> Endpoint: "Specific Character by ID" (ver interface Web da API)

---

### 🎓 Activity: Listar um Professor da Escola

- Deve permitir ao usuário informar o **nome de um professor**.
- Utilizar o endpoint correspondente na API.
- Exibir em um `TextView` os campos:
  - `name`
  - `alternate_names`
  - `species`
  - `house`

> Endpoint: "Hogwarts Staff"

---

### 🏠 Activity: Listar Estudantes de uma Casa

- Usuário escolhe uma casa usando **Radio Buttons** (apenas um selecionado por vez).
- Exibe o **nome de todos os estudantes** da casa em um `TextView`.
- Utilizar um `ScrollView` para permitir rolagem.

> Endpoint: "Characters in a House"

---

### ✨ Activity: Ver Feitiços

- Exibir todos os **feitiços** da API em uma **lista** ou `RecyclerView`.
- Ao clicar em um feitiço, abrir uma tela de detalhes com:
  - `name`
  - `description`

> Endpoint: "All Spells"

---

### ❌ Sair

- O botão deve **fechar o aplicativo**.

---