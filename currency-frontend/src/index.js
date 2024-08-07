import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import './i18n'; // Импортируйте i18n сюда

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);