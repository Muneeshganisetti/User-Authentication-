 // Get token from URL
 function getTokenFromUrl() {
     const params = new URLSearchParams(window.location.search);
     return params.get("token");
 }

 // Form submit handler
 document.getElementById("resetForm").addEventListener("submit", function (event) {
     event.preventDefault();

     const token = getTokenFromUrl();
     const newPassword = document.getElementById("newPassword").value;
     const confirmPassword = document.getElementById("confirmPassword").value;
     const messageDiv = document.getElementById("message");

     if (newPassword !== confirmPassword) {
         messageDiv.style.color = "red";
         messageDiv.textContent = "Passwords do not match!";
         return;
     }

     const payload = { token: token, newPassword: newPassword };

     fetch("http://localhost:8081/api/resetpass", {
         method: "POST",
         headers: { "Content-Type": "application/json" },
         body: JSON.stringify(payload)
     })
         .then(res => res.text())
         .then(msg => {
             messageDiv.style.color = "green";
             messageDiv.textContent = msg;
         })
         .catch(err => {
             messageDiv.style.color = "red";
             messageDiv.textContent = "Error: " + err;
         });
 });
