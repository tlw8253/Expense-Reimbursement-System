"use strict";
// need to make sure the URL is: http://localhost:5500/index.html
// live server does not do this.
let loginButton = document.getElementById('login');
let usernameInput = document.getElementById('username');
let passwordInput = document.getElementById('password');

var sUserRole = "";
var iPageCnt = 0;

function login(event) {
   // this will prevent the default behavior of what happens when a button inside a form element is clicked
    event.preventDefault();
   
    const loginInfo = {
        'username': usernameInput.value,
        'password': passwordInput.value
    };

      fetch('http://localhost:3015/ers_login', {
        method: 'POST',
        credentials: 'include', // this specifies that when you receive cookies,
        // you should include them in future requests.
        headers: {
            'Content-Type': 'application/json' // application/json is a MIME type
        },
        body: JSON.stringify(loginInfo)}).then((response) => {
        if (response.status === 200) {
          //alert("ers_login: status is 200");
          getUsercredentials();
          alert("1 sUserRole: " + sUserRole);
          if (sUserRole == "EMPLOYEE"){
            window.location.href = '/test.html';
          }
        } else if (response.status === 400) {
            displayInvalidLogin();
        }
    })
};

//let text = localStorage.getItem("testJSON");
//let obj = JSON.parse(text);

function getUsercredentials(){
  alert("getUsercredentials()");
  fetch('http://localhost:3015/ers_current_user', {
    'credentials': 'include',
    'method': 'GET'
}).then((response) => {
    if (response.status === 401) {
      //alert("ers_current_user: status 401");
        window.location.href = '/index.html'
    } else if (response.status === 200) {
      alert("getUsercredentials(): status 200");
        return response.json();
    }
}
).then((user) => {
    sUserRole = user.userRole.userRole;
    alert("2 sUserRole: " + sUserRole);
  iPageCnt++;
  if (sUserRole == "EMPLOYEE"){
    window.location.href = '/reimbursement.html';
  }

}
)};

function displayInvalidLogin() {
    let loginForm = document.getElementById('loginform');

    let p = document.createElement('p');
    p.style.color = 'red';
    p.innerHTML = 'INVALID LOGIN!';

    loginForm.appendChild(p);
};

function checkIfUserCurrentlyLoggedIn(event) {

  /*
  alert("checkIfUserCurrentlyLoggedIn()");
  if(sUserRole == "" && iPageCnt > 0){
    getUsercredentials();
  }else{
    if(sUserRole == "EMPLOYEE"){
    window.location.href = '/test.html';
    }
    else{
      alert("user role page not defined.")
    }
  }
  */
};

loginButton.addEventListener('click', login);
window.addEventListener('load', checkIfUserCurrentlyLoggedIn)