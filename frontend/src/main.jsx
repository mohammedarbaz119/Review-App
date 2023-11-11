import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import Usercontext  from './Context/UserContext.jsx'


ReactDOM.createRoot(document.getElementById('root')).render(
  <Usercontext>
  <React.StrictMode>
    <App />
  </React.StrictMode>
  </Usercontext>,
)
