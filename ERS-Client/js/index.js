"use strict";

let button = document.getElementById('btn_login');
button.addEventListener('click', retrieveDataWithFetchAsyncAwait);
const url='http://localhost:3015/ers_login_jdbc';

/**
 * Fetch API (modern way)
 */
function retrieveDataWithFetch() {
  let inputElement = document.querySelector('#username');
  let sUsername = inputElement.value;
  inputElement = document.querySelector('#password');
  let sPassword = inputElement.value;
  let sLoginURL = url + "?username=" + sUsername + "&password=" + sPassword;



    // The fetch function returns what is known as a 'Promise'
    fetch(sLoginURL, {
      'mode' : 'no-cors',
        'method': 'GET'
     }).then((data) => {
        return data.json(); // data.json() will return yet another promise
    }).then((loginObject) => {
        populateData(loginObject);
    }).catch((err) => {
        console.log(err);
    });
}

async function retrieveDataWithFetchAsyncAwait() {
    let inputElement = document.querySelector('#username');
    let sUsername = inputElement.value;
    inputElement = document.querySelector('#password');
    let sPassword = inputElement.value;
    let sLoginURL = url + "?username=" + sUsername + "&password=" + sPassword;
    alert(sLoginURL);
  
    try {
        let data = await fetch(sLoginURL, {                     
          'method': 'GET',
          'mode' : 'no-cors',          
        });

        let loginObject = await data.json();

        populateData(loginObject);
    } catch (err) {
        console.log(err);
    }

}

function populateData(data) {
alert("populateData(data)");
  /*
    let tbody = document.querySelector('#pokemonTable tbody');

    let pokemonRow = document.createElement('tr');
    let id = data.id;
    let pokemonName = data.name;
    let pokemonType = data.types[0].type.name;
    let imageUrl = data.sprites.other['official-artwork'].front_default;

    let idTd = document.createElement('td');
    idTd.innerHTML = id;

    let nameTd = document.createElement('td');
    nameTd.innerHTML = pokemonName;

    let typeTd = document.createElement('td');
    typeTd.innerHTML = pokemonType;

    let imageTd = document.createElement('td');
    imageTd.innerHTML = `<img src="${imageUrl}" width="100" height="100">`;

    pokemonRow.append(idTd);
    pokemonRow.append(nameTd);
    pokemonRow.append(typeTd);
    pokemonRow.append(imageTd);

    tbody.append(pokemonRow);
    */


    function test(){
      if(null == null){alert("null == null")}
      
    }


  }