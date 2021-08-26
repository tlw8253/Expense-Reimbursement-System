"use strict";

var sURL_Host =  "http://localhost:3015/";

var e = document.getElementById("reimb_type");
var userPwd = "";

let createButton = document.getElementById('btn_create');
let clearButton1 = document.getElementById('btn_clear_1');
let clearButton2 = document.getElementById('btn_clear_2');
let searchReimbButton = document.getElementById('btn_search_req');

let getAllRecordsInput = document.getElementById('get_all_records');
let getPendingRecordsInput = document.getElementById('get_pending_records');
let getByReimbNumberInput = document.getElementById('get_reimb_number');

let reimbAmountInput = document.getElementById('reimb_amount');
let reimbDescrpInput = document.getElementById('reimb_description');
let reimbReceiptInput = document.getElementById('reimb_receipt');


let singleResultArea = document.getElementById('single_search_results_area');
let clearSingleResultArea = document.getElementById('btn_clear_single_search_results_area');


let usernameOutput = document.getElementById('username');
let usernroleOutput = document.getElementById('userrole');
let addStatusOutput = document.getElementById('action_status');

function onPageLoad(){
  getUsercredentials();
  resetPage();
  showUserLink();
}


function getUsercredentials(){
  //alert("getUsercredentials()");
  fetch(  'http://localhost:3015/ers_current_user', { //'http://localhost:3015/ers_session_user', {
    'credentials': 'include',
    'method': 'GET'
}).then((response) => {
    if (response.status === 401) {
      console.log("Login session expired.");
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
  clearStatus();
  hideSelectItem();
  hideCreateRequest();
  document.getElementById("start_action").selectedIndex=0; 
  resetSearchArea();
  resetSingleResultArea();
  hideFormSearchResults();
}

function resetSearchArea(){
  getAllRecordsInput.checked = false;
  getPendingRecordsInput.checked = false;
  getByReimbNumberInput.value = "";
}


function hideFormSearchResults(){
  document.getElementById("all_reimb_recs").style.display="none";
  document.getElementById("div_all_recs").style.display="none";
}
function showFormSearchResults(){
  document.getElementById("all_reimb_recs").style.display="block";
  document.getElementById("div_all_recs").style.display="block";
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

  function clearStatus(){
    addStatusOutput.value = "";
  }
  function setStatusMsg(sMsg){
    addStatusOutput.value = sMsg;
  }
  
  function btn_search_req(event){
    event.preventDefault();
    clearStatus();   

    if ((getAllRecordsInput.checked == false) && (getPendingRecordsInput.checked == false) && (getByReimbNumber.value == "")){
      setStatusMsg("Please enter a search criteria.");
    }

    console.log("getAllRecordsInput.checked: [" + getAllRecordsInput.checked + "]");
    console.log("getPendingRecordsInput.checked: [" + getPendingRecordsInput.checked + "]");
    console.log("getByReimbNumberInput.value: [" + getByReimbNumberInput.value + "]");
 
    if (getAllRecordsInput.checked){
      getByReimbNumberInput.value = "";
      console.log("calling: getFilteredReimbursementRecords(" + "ALL" + ")");
      getFilteredReimbursementRecords("ALL");
    }

   if (getPendingRecordsInput.checked){
    getByReimbNumberInput.value = "";
    console.log("calling: getFilteredReimbursementRecords(" + "PENDING" + ")");
    getFilteredReimbursementRecords("PENDING");
    }

    if (getByReimbNumberInput.value != ""){
      console.log("getByReimbNumberInput(" + getByReimbNumberInput.value + ")");
      getReimbRecordByReimbNumber(getByReimbNumberInput.value);
      }
  

    resetSearchArea();
  }

 

  function getReimbRecordByReimbNumber(sReimbNum){
    console.log("getReimbRecordByReimbNumber("+sReimbNum+")");
    hideSelectItem();

    fetch( sURL_Host + 'ers_current_user', {
        'credentials': 'include',
        'method': 'GET'
    }).then((response) => {
        if (response.status === 401) {
            window.location.href = '/index.html'
        } else if (response.status === 200) {
            return response.json();
        }
    }).then((user) => {
        return fetch( sURL_Host +  `ers_user_reimb_rec/${user.username}/`+sReimbNum, {
            'method': 'GET', 
            'credentials': 'include'
        });
    }).then((response) => {
        return response.json()
    }).then((reimbursement) => {
      setSingleResultArea(reimbursement);
    })

  }

  function setSingleResultArea(objReimb){

    document.getElementById("review_rec_id").value= objReimb.reimbId; 
    document.getElementById("review_reimb_status").value= objReimb.reimbStatus.reimbStatus; 
    document.getElementById("review_reimb_type").value= objReimb.reimbType.reimbType; 
    document.getElementById("review_reimb_amount").value= objReimb.reimbAmount; 
    document.getElementById("review_reimb_receipt").value= objReimb.reimbReceipt; 
    document.getElementById("review_reimb_description").value= objReimb.reimbDescription; 
    document.getElementById("review_reimb_submitted_ts").value= new Date(objReimb.reimbSubmitted); 
    document.getElementById("review_reimb_resolved_by_un").value= objReimb.reimbResolver.username; 
    document.getElementById("review_reimb_resolved_by_role").value= objReimb.reimbResolver.userRole.userRole; 
    document.getElementById("review_reimb_resolved_msg").value= objReimb.reimbResolverMsg; 
    document.getElementById("review_reimb_resolved_ts").value= new Date(objReimb.reimbResolved);    

    showSingleResultArea();
    setStatusMsg("Reimbursement recorded received from database.");   
  }

  function resetSingleResultArea(){
    singleResultArea.style.display = "none";

    document.getElementById("review_rec_id").value=""; 
    document.getElementById("review_reimb_status").value=""; 
    document.getElementById("review_reimb_type").value=""; 
    document.getElementById("review_reimb_amount").value=""; 
    document.getElementById("review_reimb_receipt").value=""; 
    document.getElementById("review_reimb_description").value=""; 
    document.getElementById("review_reimb_submitted_ts").value=""; 
    document.getElementById("review_reimb_resolved_by_un").value=""; 
    document.getElementById("review_reimb_resolved_by_role").value=""; 
    document.getElementById("review_reimb_resolved_msg").value=""; 
    document.getElementById("review_reimb_resolved_ts").value="";    
  }

  function showSingleResultArea(){
    resetSearchArea();
    singleResultArea.style.display = "block";
  }
  
  function btn_clear_single_search_results_area(event){
    event.preventDefault();
    resetPage();

  }

  function getFilteredReimbursementRecords(sStatus){
    console.log("getFilteredReimbursementRecords(" + sStatus +")");

    fetch( sURL_Host + 'ers_current_user', {
      'credentials': 'include',
      'method': 'GET'
  }).then((response) => {
      if (response.status === 401) {
          window.location.href = '/index.html'
      } else if (response.status === 200) {
          return response.json();
      }
  }).then((user) => {
      return fetch( sURL_Host +  `ers_user_reimb_filter/${user.username}/`+sStatus, {
          'method': 'GET', 
          'credentials': 'include'
      });
  }).then((response) => {
      return response.json()
  }).then((reimbursement) => {
    populateReimbTable(reimbursement);
  })
  };


  function populateReimbTable(arrReimbursement){

    console.log("populateReimbTable(arrReimbursement)");




    
  }





  function checkIfUserCurrentlyLoggedIn(event) {
  };
  
  searchReimbButton.addEventListener('click', btn_search_req);
  createButton.addEventListener('click', btn_create);
  clearButton1.addEventListener('click', btn_clear_1);
  clearButton2.addEventListener('click', btn_clear_2);
  clearSingleResultArea.addEventListener('click', btn_clear_single_search_results_area);
  window.addEventListener('load', checkIfUserCurrentlyLoggedIn)

