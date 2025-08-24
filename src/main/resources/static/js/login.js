 document.addEventListener("DOMContentLoaded", function () {
     console.log("login.js loaded");

     document.getElementById("loginForm").addEventListener("submit", async function (e) {
         e.preventDefault();

         const payload = {
             email: document.getElementById("email").value,
             password: document.getElementById("password").value
         };

         try {
             const response = await fetch("/api/login", {
                 method: "POST",
                 headers: {
                     "Content-Type": "application/json"
                 },
                 body: JSON.stringify(payload)
             });

             const result = await response.json(); // Expecting JSON, e.g. { token: "...", message: "..." }

             if (result.token) {
                 localStorage.setItem("token", result.token); // Store token
                 document.getElementById("loginMsg").textContent =  "Login successful!";
             } else {
                 document.getElementById("loginMsg").textContent = result.message || "Login failed!";
             }

         } catch (error) {
             console.error("Login failed:", error);
             document.getElementById("loginMsg").textContent = "Login failed. Try again.";
         }
     });
 });
