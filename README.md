# Desafio Coupon API

### Documentação
Implementação de uma API Restful para gerenciamento de cupons de desconto, utilizando Spring Boot e Java.

### Tecnologias Utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven
- Swagger

### Funcionalidades
- Criar um novo cupom de desconto
- Recuperar um cupom de desconto por ID
- Remover um cupom de desconto por ID

### Como Executar Localmente (IDE indicada: IntelliJ IDEA)
1. Clone o repositório para sua máquina local.
2. Abra o projeto em sua IDE.
3. Verifique se o Java 17 está configurado como JDK do projeto.
4. ```mvn clean install``` para compilar o projeto.
5. ```mvn spring-boot:run``` para iniciar a aplicação.
6. A API estará disponível em `http://localhost:8080/api/coupons`.
7. A documentação Swagger pode ser acessada em `http://localhost:8080/swagger-ui/index.html`.

### Como Executar com Docker
1. Certifique-se de ter o Docker instalado e em execução.
2. No terminal, navegue até o diretório do projeto e execute o comando:
   ```bash
   docker build -t coupon-api .
   ```
   
3. Após a construção da imagem, execute o container com o comando:
   ```bash
   docker run -p 8080:8080 --name cupons-container coupon-api
   ```
4. A API estará disponível em `http://localhost:8080/api/coupons`.
5. A documentação Swagger pode ser acessada em `http://localhost:8080/swagger-ui/index.html`.


### Endpoints
- **POST /api/coupon**: Criar um novo cupom de desconto.
- **GET /api/coupon/{id}**: Recuperar um cupom de desconto por ID.
- **DELETE /api/coupon/{id}**: Remover um cupom de desconto por ID.
