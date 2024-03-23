# OpenLibrary: Catálogo de Livros em Kotlin
Uma aplicação móvel utilizando Kotlin sobre um Catálogo de Livros.

A aplicação irá apresentar uma lista de livros obtidos via API, permitir que o usuário marque livros de interesse ou já lidos e adicione anotações. Além disso, a aplicação irá persistir localmente os livros marcados e anotações.

## Uso

Digite um texto no campo e clicar na lupa para pesquisar um livro no catálogo:


![print-tela](https://github.com/FertwBr/OpenLibrary/assets/96090586/e1303c44-7d27-4623-a62f-a43b87bcfdc1)



## Desenvolvimento do projeto

Para desenvolver o projeto, foi utilizado **Ktor**, **Coroutines** e o padrão MVP:

**MVP (Model-View-Presenter):**

É uma arquitetura de software que separa a lógica de apresentação de uma aplicação das lógicas de manipulação de dados e da interface do usuário. Ele é composto por três componentes principais:

**Modelo:** Representa os dados e a lógica de negócios da aplicação;

**Visualização:**  Representa a interface do usuário e exibe as informações para o usuário;

**Apresentador:**  Age como um intermediário entre o Modelo e a Visualização.

No MVP, a comunicação entre esses três componentes é geralmente feita através de interfaces. O Presenter atualiza a View e o Model, mas a View não se comunica diretamente com o Model. Essa separação facilita a testabilidade do código, já que as lógicas de apresentação e de dados estão claramente definidas. Isso também facilita a manutenção e escalabilidade do código, tornando-o mais modular.

Além disso, o MVP é uma alternativa ao padrão de arquitetura MVC (Model-View-Controller) tradicional. No MVC, a maior parte da lógica de negócios reside no Controller, e durante o ciclo de vida de uma aplicação, esse arquivo cresce e se torna difícil de manter. Demais, devido aos mecanismos de UI e acesso a dados estarem fortemente acoplados, tanto a camada Controller quanto a View caem na mesma atividade ou fragmento. Isso causa problemas ao fazer alterações nos recursos da aplicação e torna difícil realizar testes unitários nas diferentes camadas, pois a maior parte das partes que estão sendo testadas precisa de componentes do SDK Android.

O padrão MVP supera esses desafios do MVC e fornece uma maneira fácil de estruturar os códigos do projeto. A razão pela qual o MVP é amplamente aceito é que ele fornece modularidade, testabilidade e um código mais limpo e fácil de manter.


 **COROUTINES:** 

Coroutines são um recurso do Kotlin que permitem executar código de forma assíncrona de uma maneira mais simples e eficiente. São baseadas em conceitos estabelecidos de outras linguagens, como a suspensão, que permite que a execução de um bloco de código seja interrompida e retomada mais tarde.

Algumas das características notáveis das coroutines incluem:

-Leveza: Você pode executar muitas coroutines em uma única thread sem bloquear a thread principal;

-Menos vazamentos de memória: As coroutines usam a concorrência estruturada para executar operações dentro de um escopo, o que ajuda a evitar vazamentos de memória;

-Suporte integrado para cancelamento: O cancelamento de uma coroutine é propagado automaticamente para outras coroutines que estão suspensas nela;

-Integração com Jetpack: Muitas bibliotecas Jetpack incluem extensões que fornecem suporte completo para coroutines.


 **KTOR:**

Ktor é um framework de programação que permite criar servidores e clientes assíncronos usando a linguagem Kotlin. As aplicações assíncronas são aquelas que podem realizar várias tarefas ao mesmo tempo, sem bloquear o sistema. Isso é importante para aplicações que precisam lidar com grandes quantidades de dados ou que precisam interagir com o mundo real.

O Ktor é uma ferramenta poderosa que pode ser usada para criar aplicações assíncronas de alta performance e escaláveis. Ele oferece uma variedade de recursos que facilitam o desenvolvimento de aplicações assíncronas, incluindo:

**-Suporte para Kotlin e Coroutines:** O Ktor é construído usando Kotlin e Coroutines, que são recursos da linguagem Kotlin que permitem a programação assíncrona de uma maneira simples e eficiente;

**-Suporte para REST:** O Ktor fornece suporte nativo para REST, um protocolo de comunicação que é amplamente utilizado em aplicações web;

**-Suporte para WebSockets:** O Ktor fornece suporte nativo para WebSockets, uma tecnologia que permite a comunicação bidirecional entre clientes e servidores;

**-Suporte para testes:** O Ktor fornece suporte para testes unitários e de integração, o que facilita a verificação da funcionalidade da sua aplicação.


## Trechos de código relevantes:

**OpenLibraryScrollListener**

A classe `OpenLibraryScrollListener` é uma subclasse de `RecyclerView.OnScrollListener()`. Ela é usada para detectar quando 
o usuário rolou até um certo ponto em uma lista de itens em um `RecyclerView`.

```
abstract class OpenLibraryScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val threshold: Int
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        if (totalItemCount == lastVisibleItemPosition + threshold) {
            onScrolledToThreshold()
        }
    }

    abstract fun onScrolledToThreshold()
}
```


**Detalhes:**

- `layoutManager: LinearLayoutManager`: Este é o gerenciador de layout associado ao RecyclerView. Ele é usado para obter informações sobre o estado atual do RecyclerView, como a posição do último item visível.

- `threshold: Int`: Este é o limite que determina quando o método `onScrolledToThreshold()` deve ser chamado. Se o número total de itens for igual à posição do último item visível mais o limite, o método `onScrolledToThreshold()` será chamado.

- `onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)`: Este método é chamado sempre que o RecyclerView é rolado. Ele verifica se o total de itens é igual à posição do último item visível mais o limite. Se for, ele chama o método `onScrolledToThreshold()`.

- `onScrolledToThreshold()`: Este é um método abstrato que deve ser implementado pela classe que estende `OpenLibraryScrollListener`. Ele é chamado quando o usuário rolou até o limite especificado.

**Uso:**

Essa classe permite que você execute uma ação específica quando o usuário rola até um certo ponto em um RecyclerView. Isso pode ser útil, por exemplo, para carregar mais itens quando o usuário chega ao final da lista.




## Instruções de instalação
Para instalar e executar o projeto, siga os passos abaixo:

1. **Instale o Android Studio**: O Android Studio é a IDE oficial para o desenvolvimento de aplicativos Android e inclui tudo o que você precisa para construir aplicativos para Android. Você pode baixá-lo aqui: https://developer.android.com/studio
   
2. **Instale o Kotlin Plugin**: No Android Studio, vá para `File > Settings > Plugins`, procure por `Kotlin` e instale-o. (Você precisará reiniciar o IDE após a instalação do plugin.)

3. Acesse o repositório ‘OpenLibrary’.

4. Baixe o arquivo do código, preferencialmente como arquivo zip compactado.

5. Extraia todo o arquivo.

6. Abra-o dentro do Android Studio.

7. Selecione o botão ‘Run app’.

## Contribuição
O projeto não aceita contribuições, pois é apenas um trabalho para aprendizado, e não algo que será lançado.

## Licença
Este projeto não possui licenças.


