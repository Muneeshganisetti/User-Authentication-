 document.addEventListener("DOMContentLoaded", function () {
     console.log("Signup.js loaded!");

     document.getElementById('signupForm').addEventListener('submit', async function (e) {
         e.preventDefault();

         const payload = {
             firstname: document.getElementById('firstname').value,
             lastname: document.getElementById('lastname').value,
             email: document.getElementById('email').value,
             password: document.getElementById('password').value,
             role: document.getElementById('role').value,
         };

         try {
             const response = await fetch('/api/signup', {
                 method: 'POST',
                 headers: {
                     'Content-Type': 'application/json'
                 },
                 body: JSON.stringify(payload)
             });

             const result = await response.text();
             document.getElementById('signupMsg').textContent = result;
         } catch (error) {
             console.error('Signup failed:', error);
             document.getElementById('signupMsg').textContent = 'Signup failed. Please try again.';
         }
     });
 });
