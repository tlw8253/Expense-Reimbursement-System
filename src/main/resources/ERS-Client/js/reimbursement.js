"use strict";

var e = document.getElementById("reimb_type");

let createButton = document.getElementById('btn_create');
let editButton = document.getElementById('btn_edit');
//let reimbTypeInput = e.value; //e.options[e.selectedIndex].text; //e.options[e.selectedIndex].value; //document.getElementById('reimb_type');
let reimbAmountInput = document.getElementById('reimb_amount');
let reimbDescrpInput = document.getElementById('reimb_description');
let reimbReceiptInput = document.getElementById('reimb_receipt');

let usernameOutput = document.getElementById('username');
let usernroleOutput = document.getElementById('userrole');
let addStatusOutput = document.getElementById('action_status');

function onPageLoad(){
  getUsercredentials();
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

}
)};

function btn_edit(event) {
  event.preventDefault();
  alert("btn_edit event");
}

function btn_create(event) {
   // this will prevent the default behavior of what happens when a button inside a form element is clicked
   event.preventDefault();
   //reimbTypeInput = e.options[e.selectedIndex].value;

   //alert("e.value: " + e.options[e.selectedIndex].text);

   const createRequest = {       
       'reimbType': e.options[e.selectedIndex].text, //need to do this here and not outside like the other vars. //e.value, //reimbTypeInput.value,
       'reimbAmount': reimbAmountInput.value,
       'reimbDescription': reimbDescrpInput.value,
       'reimbReceipt': reimbReceiptInput.value
   };

   console.log("create(event): createRequest.reimbType: " + createRequest.reimb_type);
   console.log("create(event): createRequest.reimbAmount: " + createRequest.reimb_amount);
   console.log("create(event): createRequest.reimbDescription: " + createRequest.reimb_description);
   //alert("create(event): createRequest.reimb_amount: " + createRequest.reimb_amount);


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


function submitReimburstment(){
  fetch('http://localhost:3015/ers_current_user', {
    'credentials': 'include',
    'method': 'POST'
}).then((response) => {
    if (response.status === 401) {
         window.location.href = '/index.html'
    } else if (response.status === 200) {
        return response.json();
    }
}
).then((user) => {
    sUserRole = user.userRole.userRole;
   if (sUserRole == "EMPLOYEE"){
    window.location.href = '/reimbursement.html';
  }
}
)
};


function testFunc(){
  fetch('http://localhost:3015/ers_reimb_add/' + usernameOutput.value, {
    method: 'POST',
    credentials: 'include', //must include on calls
    headers: {
        'Content-Type': 'application/json' 
    },
    body: JSON.stringify(createRequest)}).then((response) => {
    if (response.status === 200) {
      processReimbObj();
      if (sUserRole == "EMPLOYEE"){
        window.location.href = '/test.html';
      }
    } else if (response.status === 400) {
        displayInvalidLogin();
    }
})

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
    document.getElementById("selec_request_type").style.display="block";
    document.getElementById("create_request").style.display="block";    
    enableCreateRequest();
  }
  function hideCreateRequest() {
    document.getElementById("selec_request_type").style.display="none";
    document.getElementById("create_request").style.display="none";    
  }
  function disableCreateRequest() {
    document.getElementById("btn_create").style.display="none";
    document.getElementById("btn_edit").style.display="inline";    
  }
  function enableCreateRequest() {
    document.getElementById("btn_create").style.display="inline";
    document.getElementById("btn_edit").style.display="none"; 
    document.getElementById("reimb_type").selectedIndex=0;    
    document.getElementById("reimb_amount").value=""; 
    document.getElementById("reimb_receipt").value=""; 
    document.getElementById("reimb_description").value=""; 
    document.getElementById("action_status").value="";    
  }

  function checkIfUserCurrentlyLoggedIn(event) {
  };

  createButton.addEventListener('click', btn_create);
  editButton.addEventListener('click', btn_edit);
  window.addEventListener('load', checkIfUserCurrentlyLoggedIn)