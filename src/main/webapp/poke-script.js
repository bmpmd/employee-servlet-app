//grab all the elements from the page to work with 
const pokeId = document.getElementById('poke-id')
const respId = document.getElementById('resp-id')
const pokeName = document.getElementById('resp-name')
const pokeImg = document.getElementById('resp-sprite')
const button = document.querySelector('button')// query selector bc 


//if i hafve a CORS error, 

// create a fucntion to fetch a poke object
function fetchPokemon() {
	//capture inpout from the doc 
	let idNum = pokeId.value;//capturing the val of the input element 

	// send a fetch call to the pokeAPI and concat the val of t he pokemon we wnat 
	// chain functions to our promise -> parse the json into an object .then call the function on the obj 
	fetch(`https://pokeapi.co/api/v2/pokemon/${idNum}`)
		.then(response => response.json())
		//.then(obj => console.log(obj)) // do this to test !!
		.then(renderPokemon)



}

// create a function to RENDER the respones 
function renderPokemon(data) {
	//set all the elements we captured above EQUAL TO 
	//the props we pull from the data 
	pokeName.innerHTML = `Name: ${data.name}`;
	respId.innerHTML = `Id: ${data.id}`;

	pokeImg.setAttribute('src', data.sprites.front_default);
	pokeImg.setAttribute('height', 300)//we can set attributes of resp-sprite to anytrhing we can set HTML attributes to 


}
//add eventlistener to the button 
button.addEventListener('click', fetchPokemon)