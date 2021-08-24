"use strict";

let searchButton = document.getElementById('btn_search');
let clearButton = document.getElementById('btn_clear');

let reimbNumber = document.getElementById('reimbursement_number');
let empUsername = document.getElementById('employee_username');
let allRecords = document.getElementById('all_records');

let usernameOutput = document.getElementById('username');
let usernroleOutput = document.getElementById('userrole');



function onPageLoad(){
    getUsercredentials();
    hideFormSearchResults();
    hideFormReviewReimbRec();
    showFormSelectForSearchReq();
  };

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
  
  }
  )
};

  function btn_search(event){
    event.preventDefault();
    console.log("reimbNumber: " + reimbNumber.value);
    console.log("empUsername: " + empUsername.value);
    console.log("allRecords: " + allRecords.checked);
      //alert("btn_search()");

      
      if(reimbNumber.value){
        searchByReimbNumber(reimbNumber.value);
      }
  }
  function btn_clear(event){
    event.preventDefault();
    reimbNumber.value = "";
    empUsername.value = "";
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
        showFormReviewReimbRec();


    
    }
    )
};


  function checkIfUserCurrentlyLoggedIn(event) {
};
  searchButton.addEventListener('click', btn_search);
  window.addEventListener('load', checkIfUserCurrentlyLoggedIn)