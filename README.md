Cafeteria System API
Sistema desenvolvido para gerenciamento de uma cafeteria, permitindo o controle de estoque de produtos e processamento de pedidos com upload de comprovantes.

🗄️ Modelagem do Banco de Dados

Para garantir a integridade dos pedidos e dos produtos, a arquitetura do banco de dados foi desenhada seguindo as regras de negócio da cafeteria.

Modelo Conceitual
![Modelo Conceitual da Cafeteria](assets/modelo-conceitual.png)

Modelo Lógico
![Modelo Lógico da Cafeteria](assets/modelo-logico.png)

🚀 Funcionalidades
Produtos: Cadastro completo com imagem (armazenada localmente).
Pedidos: Registro de vendas com múltiplos itens e cálculo automático.
Uploads: Endpoints específicos para fotos de produtos e comprovantes de PIX.
Segurança: Autenticação via Spring Security (Basic Auth).

🛠️ Tecnologias
Java, Spring Boot, MySQL, Hibernate, JPA.

📋 Endpoints Principais
POST, GET, PUT, DELETE /produtos - Gerenciamento completo de produtos.
Produtos
GET /produtos - Lista todos os produtos.
POST /produtos/{id}/upload - Faz o upload da imagem do produto.

📁 Arquitetura MVC

src/main/java/com/cafeteria/sistema/
├── entidades/ → Produtos, Pedido
├── repositories/ → Interfaces JPA
├── services/ → Lógica de negócio
├── controllers/ → Endpoints REST
├── exceptions/ → Exceções personalizadas
├── handler/ → Tratamento global de erros
└── config/ → Security

🔐 Configuração de Acesso
O sistema está protegido. Utilize as credenciais abaixo no Thunder Client ou no Postman (Auth -> Basic):
User: cafeteria
Password: cafe