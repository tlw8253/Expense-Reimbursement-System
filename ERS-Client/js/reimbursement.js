

function onPageLoad() {
    //do nothing for now
  }

function startAction(selectObject){
     var value = selectObject.value;  
    if (value == 'view'){        
        hideCreateRequest();
        showSelectItem();
    }
    if (value == 'create'){        
        hideSelectItem();
        showCreateRequest();
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
  }
  function hideCreateRequest() {
    document.getElementById("selec_request_type").style.display="none";
    document.getElementById("create_request").style.display="none";    
  }