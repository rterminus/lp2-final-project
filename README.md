# LP2 Final Project - RPG Adventure API

API REST em Spring Boot com combate, inventário, quests e uma
árvore de decisão (árvore binária) para a narrativa.

## Equipe
- Álvaro Barbosa Filho - 20250028947
- Gabriel Brandão Vilar - 20250035487
- Luiz Renan da Silva Pontes - 20250031601
- Pedro Lucio de Azevedo - 20250036831

## Requisitos
- Java 17 ou superior
- Maven (ou o `./mvnw` incluído no projeto)

## Como executar
```
./mvnw spring-boot:run
```
A API sobe em http://localhost:8080

## Como rodar os testes
```
./mvnw test
```

## Endpoints
| Método | Rota | Descrição |
|---|---|---|
| GET | /adventurer/status | Aventureiro ativo |
| GET | /adventurer | Lista aventureiros |
| POST | /combat/attack | Executa um turno de combate |
| GET | /inventory | Inventário do aventureiro |
| POST | /inventory/add | Adiciona item |
| GET | /inventory/filter?rarity=RARE | Filtra itens por raridade (Stream) |
| POST | /items/use/{itemName} | Usa um item |
| GET | /adventure/current | Cenário atual da árvore |
| POST | /adventure/choose/left | Caminho da esquerda |
| POST | /adventure/choose/right | Caminho da direita |
| POST | /adventure/reset | Reinicia a aventura |
