
let amigos=[];

function adicionarAmigo(){
    let nomeAmigo = document.getElementById("amigo").value;
    //Validação
    if(nomeAmigo=="")
        alert("Por favor, insira um nome.")
    //Inserção do nome na lista
    else{
        amigos.push(nomeAmigo);
        document.getElementById("amigo").value = "";
    }
    adicionarNomes();
}
function adicionarNomes(){
    let lista = document.getElementById("listaAmigos");
    lista.innerHTML = "";
    //Para cada elemento do array, será adicionada uma linha na lista em exibição
    amigos.forEach(element => {
        lista.innerHTML = lista.innerHTML + "<li>" + element + "</li>";
    });
}
function sortearAmigo(){
    //Validação
    if(amigos.length == 0)
        alert("Não há amigos para sortear");
    //Escolha do número aleatório
    let indice = Math.floor(Math.random() * amigos.length);
    
    //Exibição do nome
    document.getElementById("resultado").innerHTML ="Seu amigo secreto é: " + amigos[indice];

}
function novoSorteio(){
    location.reload();
}