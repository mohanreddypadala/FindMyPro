document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            username: document.getElementById('username').value,
            password: document.getElementById('password').value
        })
    });

    if (response.ok) {
        const user = await response.json();
        
        // Redirect based on user type
        if (user.userType === 'CUSTOMER') {
            window.location.href = '/html/customer/dashboard.html';
        } else {
            window.location.href = '/html/provider/dashboard.html';
        }
    }
});