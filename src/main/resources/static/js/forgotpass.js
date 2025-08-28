 document.getElementById("forgotForm").addEventListener("submit", async function (e) {
     e.preventDefault();

     const email = document.getElementById("email").value;
     const messageDiv = document.getElementById("forgotMsg");

     try {
         const response = await fetch("http://localhost:8081/api/forgotpass", {
             method: "POST",
             headers: { "Content-Type": "application/json" },
             body: JSON.stringify({ email: email })
         });

         if (response.ok) {
             messageDiv.style.color = "green";
             messageDiv.textContent = "Password reset link sent to your email.";
         } else {
             const result = await response.text();
             messageDiv.style.color = "red";
             messageDiv.textContent = result || "Error sending reset link.";
         }
     } catch (error) {
         console.error("Error:", error);
         messageDiv.style.color = "red";
         messageDiv.textContent = "Error sending reset link. Try again.";
     }
 });
