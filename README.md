# Projeto de Estacionamento

Este é um projeto de exemplo para um sistema de gerenciamento de estacionamento. Ele permite registrar a entrada e saída de veículos, calcular o custo total e exibir uma lista de veículos estacionados.

## Requisitos

- Java 17 ou superior
- Maven 3.6.3 ou superior

## Configuração do Ambiente de Desenvolvimento

1. **Clone o repositório:**



2. **Instale as dependências do projeto:**

    ```sh
    ./mvnw clean install -DskipTests
    ```

3. **Execute o projeto:**

    ```sh
    ./mvnw spring-boot:run
    ```

## Uso

### Registrar Entrada

Para registrar a entrada de um veículo, preencha o formulário com a placa e o custo por hora e clique em "Registrar Entrada".

### Registrar Saída

Para registrar a saída de um veículo, preencha o formulário com o ID do veículo e clique em "Registrar Saída". Se a saída for registrada com sucesso, o preço total pago será exibido em verde.

### Exibir Lista de Veículos

A página principal exibe uma lista de todos os veículos atualmente estacionados, incluindo informações como ID, placa, entrada, saída, custo por hora, preço inicial, preço final e status de pagamento.

## Estrutura do Projeto

- **src/main/java/com/msc/parking/controller/VeiculoController.java**: Controlador responsável por lidar com as requisições HTTP.
- **src/main/java/com/msc/parking/service/VeiculoService.java**: Serviço responsável pela lógica de negócios.
- **src/main/java/com/msc/parking/model/Veiculo.java**: Modelo que representa um veículo.
- **src/main/java/com/msc/parking/repository/VeiculoRepository.java**: Repositório responsável pela interação com o banco de dados.
- **src/main/resources/templates/veiculos.html**: Template Thymeleaf para exibir a lista de veículos e os formulários de entrada e saída.


## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.