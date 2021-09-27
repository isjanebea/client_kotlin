# Poc

## WebClient

### Configuracao Default

```
@Bean
	fun webClient( builder: WebClient.Builder): WebClient {

		return builder
			.baseUrl(baseUrl)
			.defaultHeader("autorizhation", "Baerer  $token")
			.defaultHeader("User-Agent",  "webClient test")
			.build()
	}
```


Injetando a dependência

```
@Service
class webClientService {
    @Autowired
    private lateinit var webClient: WebClient
}
```

### Exemplo de Get com todo o corpo da Resposta

```

```

# client_kotlin
