"use strict";

var e = document.getElementById("reimb_type");
var userPwd = "";

let createButton = document.getElementById('btn_create');
let clearButton1 = document.getElementById('btn_clear_1');
let clearButton2 = document.getElementById('btn_clear_2');
//let reimbTypeInput = e.value; //e.options[e.selectedIndex].text; //e.options[e.selectedIndex].value; //document.getElementById('reimb_type');
let reimbAmountInput = document.getElementById('reimb_amount');
let reimbDescrpInput = document.getElementById('reimb_description');
let reimbReceiptInput = document.getElementById('reimb_receipt');

let usernameOutput = document.getElementById('username');
let usernroleOutput = document.getElementById('userrole');
let addStatusOutput = document.getElementById('action_status');

function onPageLoad(){
  getUsercredentials();
  showUserLink();
}

function getUsercredentials(){
  //alert("getUsercredentials()");
  fetch(  'http://localhost:3015/ers_current_user', { //'http://localhost:3015/ers_session_user', {
    'credentials': 'include',
    'method': 'GET'
}).then((response) => {
    if (response.status === 401) {
      alert("Login session expired.");
         window.location.href = '/index.html'
    } else if (response.status === 200) {
        return response.json();
    }
}
).then((user) => {
  usernameOutput.value = user.username;
  usernroleOutput.value = user.userRole.userRole;
  userPwd = user.password;
  showUserLink();
}
)};

function gotoFinance(){
  window.location.href = '/finance.html';
}
function showUserLink(){
  document.getElementById("logout_link").style.display="none"; 
  document.getElementById("right_links").style.display="none"; 
  if (usernroleOutput.value == "FINANCEMGR"){
    document.getElementById("right_links").style.display="block"; 
  }else{
    document.getElementById("logout_link").style.display="block"; 
  }
}

function logout(){
    
  const logoutInfo = {
      'username': usernameOutput.value,
      'password': userPwd
  };

    fetch('http://localhost:3015/ers_logout', {
      method: 'POST',
      credentials: 'include', // this specifies that when you receive cookies,
      // you should include them in future requests.
      headers: {
          'Content-Type': 'application/json' // application/json is a MIME type
      },
      body: JSON.stringify(logoutInfo)}).then(() => {
        window.location.href = '/index.html';

  })
};

function btn_clear_1(event) {
  event.preventDefault();
  resetPage();
}
function btn_clear_2(event) {
  event.preventDefault();
  resetPage();
}
function resetPage(){
  addStatusOutput.value = "";
  hideSelectItem();
  hideCreateRequest();
  document.getElementById("start_action").selectedIndex=0; 
  resetSearchArea();
}

function resetSearchArea(){
  document.getElementById("all_records").checked = false;
  document.getElementById("all_pending_records").checked = false;
  document.getElementById("reimbursement_number").value = "";
}


function btn_create(event) {
   // this will prevent the default behavior of what happens when a button inside a form element is clicked
   event.preventDefault();
   
   const createRequest = {       
       'reimbType': e.options[e.selectedIndex].text, //need to do this here and not outside like the other vars. //e.value, //reimbTypeInput.value,
       'reimbAmount': reimbAmountInput.value,
       'reimbDescription': reimbDescrpInput.value,
       'reimbReceipt': reimbReceiptInput.value
   };

   console.log("create(event): createRequest.reimbType: " + e.options[e.selectedIndex].text);
   console.log("create(event): createRequest.reimbAmount: " + reimbAmountInput.value);
   console.log("create(event): createRequest.reimbDescription: " + reimbReceiptInput.value);
   
   fetch('http://localhost:3015/ers_reimb_add/' + usernameOutput.value, {
    method: 'POST',
    credentials: 'include', //must include on calls
    headers: {
        'Content-Type': 'application/json' 
    },
    body: JSON.stringify(createRequest)}).then((response) => {
    if (response.status === 200) {
      processReimbObj();
   } else if (response.status === 400) {
    addStatusOutput.value = "Issue performing reimburstment record action.";
    }
})
};

function processReimbObj(){
  addStatusOutput.value = "Reimburstment record action sucessfully.";
  disableCreateRequest();
};


function startAction(selectObject){
     var value = selectObject.value;  

    //alert(value);
    if (value == 'view'){        
        hideCreateRequest();
        showSelectItem();
    }
    if (value == 'create'){        
        hideSelectItem();
        showCreateRequest();
    }    
    if (value == 'default'){        
      hideSelectItem();
      hideCreateRequest();
  }    

}


function showSelectItem() {    
    document.getElementById("select_item").style.display="block";    
  }
  function hideSelectItem() {    
    document.getElementById("select_item").style.display="none";    
  }

  function showCreateRequest() {
    document.getElementById("select_request_type").style.display="block";
    document.getElementById("create_request").style.display="block";    
    enableCreateRequest();
  }
  function hideCreateRequest() {
    document.getElementById("select_request_type").style.display="none";
    document.getElementById("create_request").style.display="none";    
  }
  function disableCreateRequest() {
    document.getElementById("btn_create").style.display="none";        
    document.getElementById("lbl_action_btn").style.display="none";
  }
  function enableCreateRequest() {
    document.getElementById("btn_create").style.display="inline";
    document.getElementById("btn_clear_1").style.display="inline"; 
    document.getElementById("reimb_type").selectedIndex=0;    
    document.getElementById("reimb_amount").value=""; 
    document.getElementById("reimb_receipt").value=""; 
    document.getElementById("reimb_description").value=""; 
    document.getElementById("action_status").value="";    
    document.getElementById("lbl_action_btn").style.display="inline";
  }

  function checkIfUserCurrentlyLoggedIn(event) {
  };

  createButton.addEventListener('click', btn_create);
  clearButton1.addEventListener('click', btn_clear_1);
  clearButton2.addEventListener('click', btn_clear_2);
  window.addEventListener('load', checkIfUserCurrentlyLoggedIn)