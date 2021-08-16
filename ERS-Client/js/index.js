function validateForm() {
    let x = document.forms["login"]["username"].value;
    if (x == "") {
      alert("Please enter a username.");
      return false;
    }

    let x = document.forms["login"]["password"].value;
    if (x == "") {
      alert("Please enter a password.");
      return false;
    }

}


  