document.getElementById('registerBtn').addEventListener('click', async () => {
    const user = {
        username: "testuser",
        password: "password123",
        email: "user@example.com",
        userType: "USER"
    };
    
    try {
        const response = await fetch('/api/users/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(user)
        });
        const data = await response.json();
        document.getElementById('response').innerHTML = 
            `<strong>User registered:</strong><pre>${JSON.stringify(data, null, 2)}</pre>`;
    } catch (error) {
        document.getElementById('response').innerHTML = `Error: ${error.message}`;
    }
});

document.getElementById('getProvidersBtn').addEventListener('click', async () => {
    try {
        const response = await fetch('/api/providers/');
        const data = await response.json();
        document.getElementById('response').innerHTML = 
            `<strong>Providers:</strong><pre>${JSON.stringify(data, null, 2)}</pre>`;
    } catch (error) {
        document.getElementById('response').innerHTML = `Error: ${error.message}`;
    }
});