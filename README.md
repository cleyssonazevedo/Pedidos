# Pedidos
Spring Boot API para a recepção de pedidos dos clientes.

**Observações**: As configurações de banco de dados estão no arquivo ***application.properties*** tanto na pasta da aplicação quanto na de teste.

* Criar um serviço que receba pedidos no formato xml e json com 6 campos:
    * número controle - número aleatório informado pelo cliente.
    * data cadastro (opcional) 
    * nome - nome do produto
    * valor - valor monetário unitário produto
    * quantidade (opcional) - quantidade de produtos.
    * codigo cliente - identificação numérica do cliente.
    
* Critérios aceitação e manipulação do arquivo:
    * O arquivo pode conter 1 ou mais pedidos, limitado a 10.
    * Não poderá aceitar um número de controle já cadastrado.
    * Caso a data de cadastro não seja enviada o sistema deve assumir a data atual.
    * Caso a quantidade não seja enviada considerar 1.
    * Caso a quantidade seja maior que 5 aplicar 5% de desconto no valor total, para quantidades a partir de 10 aplicar 10% de desconto no valor total.
    * O sistema deve calcular e gravar o valor total do pedido.
    * Assumir que já existe 10 clientes cadastrados, com códigos de 1 a 10.

**Criar um serviço onde possa consultar os pedidos enviados pelos clientes.**
* O retorno deve trazer todos os dados do pedido.
* Filtros da consulta:
    * número pedido 
    * data cadastro
    * todos


## Enviando dados
### /pedido (POST)
Envio do pedido e para processamento e gravação de dados no banco. Ele necessita declarar seu cabeçalho senão ele irá retornar um erro 415, informando que não pode receber este dado. Por padrão, ele recebe por JSON mas para enviar o Content-Type é obrigatório, como o exemplo:

* **Content-Type**: application/json
* **Accept**: application/json

Onde ele envia e recebe por json do mesmo endereço, caso queira enviar por json e receber por xml, somente altere o Accept.

#### JSON (Exemplo)
```json
{
  "id": 1,
  "cadastro": "08/07/2017",
  "cliente":{
    "id": 1
  },
  "produtos": [
    {
      "nome": "Produto",
      "valor": 1.00,
      "quantidade": 1
    }
  ]
}
```
 
#### XML (Exemplo)
```xml
<pedido>
	<id>1</id>
	<cadastro>10/07/2017</cadastro>
	<cliente>
		<id>1</id>
	</cliente>
	<produtos>
		<produto>
			<nome>Produto</nome>
			<valor>1.00</valor>
			<quantidade>1</quantidade>
		</produto>
	</produtos>
</pedido>
```

##### Respostas
* **CREATED (201)**: (Com corpo do pedido com total calculado)
* **NoContent (204)**: (Caso não tenha Cliente) (Sem corpo)
* **BadRequest (400)**: (Em caso de envio fora das especificações)
* **Internal Server Error (500)**: (Em caso de falha não especificada)

## Busca por ID Pedido
### /pedido/{id} (GET)
Número do pedido do cliente

### JSON (Exemplo de dados recebidos)
* Endereço: /pedido/1
```json
{
    "id": 1,
    "cadastro": "10/07/2017",
    "cliente": {
        "id": 1,
        "nome": "Nome Cliente"
    },
    "produtos": [
        {
            "id": 1,
            "nome": "Produto",
            "valor": 1,
            "quantidade": 1
        }
    ],
    "total": 1
}
```

### XML (Exemplo de dados recebidos
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<pedido>
    <id>1</id>
    <cadastro>10/07/2017</cadastro>
    <cliente>
        <id>1</id>
        <nome>Nome Cliente</nome>
    </cliente>
    <produtos>
        <produto>
            <id>1</id>
            <nome>Produto</nome>
            <quantidade>1</quantidade>
            <valor>1.00</valor>
        </produto>
    </produtos>
    <total>1.00</total>
</pedido>
```

##### Respostas
* **OK (200)**: (Com corpo do pedido com total)
* **NoContent (204)**: (Caso não tenha Cliente) (Sem corpo)

## Buscar por Data de Cadastro
### /pedido/data (POST)
#### JSON (Exemplo)
Agora o campo cadastro é requerido
```json
  {
    "cadastro": "10/07/2017"
  }
```

#### XML (Exemplo)
```xml
<pedido>
  <cadastro>10/07/2017</cadastro>
</pedido>
```

##### Respostas
* **OK (200)**: (Lista com todos os pedidos com esta data)
* **NoContent (204)**: (Caso não tenha Cliente) (Sem corpo)

## Listar todos os pedidos
### /pedido (GET)
#### JSON (Exemplo)
```json
{
    "pedidos": [
        {
            "id": 1,
            "cadastro": "10/07/2017",
            "cliente": {
                "id": 1,
                "nome": "Nome Cliente"
            },
            "produtos": [
                {
                    "id": 1,
                    "nome": "Produto",
                    "valor": 1,
                    "quantidade": 1
                }
            ],
            "total": 1
        }
    ]
}
```

#### XML (Exemplo)
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<pedidos>
    <pedidos>
        <pedido>
            <id>1</id>
            <cadastro>10/07/2017</cadastro>
            <cliente>
                <id>1</id>
                <nome>Nome Cliente</nome>
            </cliente>
            <produtos>
                <produto>
                    <id>1</id>
                    <nome>Produto</nome>
                    <quantidade>1</quantidade>
                    <valor>1.00</valor>
                </produto>
            </produtos>
            <total>1.00</total>
        </pedido>
    </pedidos>
</pedidos>
```
##### Respostas
* **OK (200)**: (Lista com todos os pedidos)
* **NoContent (204)**: (Caso não tenha Cliente) (Sem corpo)
