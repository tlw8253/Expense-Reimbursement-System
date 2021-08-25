"use strict";
var e = document.getElementById("reimb_status_code");
var userPwd = "";
var fmUsername = "";
var authorUsername = "";


let searchButton = document.getElementById('btn_search');
let clearButton = document.getElementById('btn_clear');

let fmSubmitButton = document.getElementById('btn_submit_fm_action');
let fmClearButton = document.getElementById('btn_clear_fm_action');

let actionStatusOutput = document.getElementById('action_status');

let reimbNumber = document.getElementById('reimbursement_number');
let rempUsername = document.getElementById('employee_username');
let allRecords = document.getElementById('all_records');

let usernameOutput = document.getElementById('username');
let usernroleOutput = document.getElementById('userrole');


function onPageLoad(){
    getUsercredentials();
    resetPage();        
  };


  function gotoReimbursement(){
    window.location.href = '/reimbursement.html';
  }

  function logout(){
    //document.event.preventDefault();
     
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


  function resetPage(){
    hideFinMgrActionBtn();
    hideFinMgrDenyReason();
    hideFormSearchResults();
    hideFormSelectForSearchReq();
    hideFormReviewReimbRec();
    showFormSelectForSearchReq();
    clearStatus();    
  }

  function clearStatus(){
    actionStatusOutput.value = "";
  }

  function btn_clear_fm_action(event) {
    event.preventDefault();
    resetPage();
  }


  function btn_submit_fm_action(event) {
    event.preventDefault();
    clearStatus();
    //reimbTypeInput = e.options[e.selectedIndex].value;
 
    //alert("e.value: " + e.options[e.selectedIndex].text);
 
    const createRequest = {       
        'reimbId': reimbNumber.value,
        'reimbStatus': e.options[e.selectedIndex].text,
        'reimbDenyReason': document.getElementById('finance_reveiw_deny_reason').value
    };
 
    console.log("create(event): e.options[e.selectedIndex].text,: " + e.options[e.selectedIndex].text,);
    console.log("create(event): document.getElementById('finance_reveiw_deny_reason').value: " + document.getElementById('finance_reveiw_deny_reason').value);
 
 
    fetch('http://localhost:3015/ers_reimb_fm_update/' + usernameOutput.value, {
     method: 'POST',
     credentials: 'include', //must include on calls
     headers: {
         'Content-Type': 'application/json' 
     },
     body: JSON.stringify(createRequest)}).then((response) => {
     if (response.status === 200) {
       processReimbObj();
    } else if (response.status === 400) {
      actionStatusOutput.value = "Issue performing reimburstment record action.";
     }
 })

 };
 
 function processReimbObj(){
  actionStatusOutput.value = "Reimburstment record action sucessfully.";
  //disableCreateRequest();
};


  function startFinMgrAction(selectObject){
    var value = selectObject.value; 
    clearStatus();

    //alert("startFinMgrAction()" + value);
    if (value == 'denied'){        
      showFinMgrDenyReason();
    } else {
      hideFinMgrDenyReason();
    }
    
    if (value == 'default'){
      hideFinMgrActionBtn();
    } else {
      showFinMgrActionBtn();
    }
  };

  function showFinMgrActionBtn(){
    document.getElementById("lbl_action_btn").style.display="block";
  }
  function hideFinMgrActionBtn(){
    document.getElementById("lbl_action_btn").style.display="none";
    document.getElementById("reimb_status_code").selectedIndex=0;
  }

  function showFinMgrDenyReason(){
    document.getElementById("finance_reveiw_deny_reason").value = "";
    document.getElementById("finance_reveiw_deny_reason_div").style.display="block"; 
  }
  function hideFinMgrDenyReason(){
    document.getElementById("finance_reveiw_deny_reason_div").style.display="none"; 
  }


  function hideFormSearchResults(){
    document.getElementById("search_all_results").style.display="none";
  }
  function showFormSearchResults(){
    document.getElementById("search_all_results").style.display="block";
  }

  function hideFormSelectForSearchReq(){
    document.getElementById("select_for_search_req").style.display="none";
  }
  function showFormSelectForSearchReq(){
    document.getElementById("reimbursement_number").value="";
    document.getElementById("employee_username").value="";
    document.getElementById("all_records").checked = false;
    document.getElementById("select_for_search_req").style.display="block";
  }

  function hideFormReviewReimbRec(){
    document.getElementById("review_reimb_record").style.display="none";
  }
  function showFormReviewReimbRec(){
    document.getElementById("review_reimb_record").style.display="block";
  }


  function getUsercredentials(){
    fetch('http://localhost:3015/ers_current_user', {
      'credentials': 'include',
      'method': 'GET'
  }).then((response) => {
      if (response.status === 401) {
           window.location.href = '/index.html'
      } else if (response.status === 200) {
          return response.json();
      }
  }
  ).then((user) => {
    usernameOutput.value = user.username;
    usernroleOutput.value = user.userRole.userRole;
    userPwd = user.password;
  }
  )
};

  function btn_search(event){
    event.preventDefault();
    clearStatus();
     
    if ((reimbNumber.value == "") && (rempUsername.value == "") && (allRecords.checked == false)){
      actionStatusOutput.value = "Please enter a search criteria.";
    } 
      
      if(reimbNumber.value){
        searchByReimbNumber(reimbNumber.value);
      }
  }
  function btn_clear(event){
    event.preventDefault();
    reimbNumber.value = "";
    rempUsername.value = "";
    allRecords.checked = false;
  }

function searchByReimbNumber(searchByReimbNumber){
 
    fetch('http://localhost:3015/ers_reimb_id/'+searchByReimbNumber, {
        'credentials': 'include',
        'method': 'GET'
    }).then((response) => {
        if (response.status === 401) {
             window.location.href = '/index.html'
        } else if (response.status === 200) {
            return response.json();
        }
    }
    ).then((Reimbursement) => {
        hideFormSelectForSearchReq();
        document.getElementById('review_rec_id').value = Reimbursement.reimbId;
        document.getElementById('review_reimb_status').value = Reimbursement.reimbStatus.reimbStatus;
        document.getElementById('review_reimb_type').value = Reimbursement.reimbType.reimbType;
        document.getElementById('review_reimb_amount').value = Reimbursement.reimbAmount;
        document.getElementById('review_reimb_author_un').value = Reimbursement.reimbAuthor.username;
        document.getElementById('review_reimb_author_role').value = Reimbursement.reimbAuthor.userRole.userRole;
        document.getElementById('review_reimb_description').value = Reimbursement.reimbDescription;
        document.getElementById('review_reimb_receipt').value = Reimbursement.reimbReceipt;

        document.getElementById('review_reimb_submitted').value = new Date(Reimbursement.reimbSubmitted);

        fmUsername = usernameOutput.value;
        authorUsername = document.getElementById("review_reimb_author_un").value
        //alert(fmUsername + " == " + authorUsername);

        document.getElementById("fin_mgr_actions").style.display="block";
        if(fmUsername == authorUsername){
          actionStatusOutput.value = "You can only view your own Reimbursement Record.";
          document.getElementById("fin_mgr_actions").style.display="none";
        }

        showFormReviewReimbRec();    
    }
    )
};


  function checkIfUserCurrentlyLoggedIn(event) {
    event.preventDefault();
};
  searchButton.addEventListener('click', btn_search);
  fmSubmitButton.addEventListener('click', btn_submit_fm_action);
  fmClearButton.addEventListener('click', btn_clear_fm_action);
  window.addEventListener('load', checkIfUserCurrentlyLoggedIn)