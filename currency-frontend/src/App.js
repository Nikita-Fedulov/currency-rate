import React, { useState } from 'react';
import axios from 'axios';
import currencies from './currencies.json';
import './App.css';

function App() {
  const [inputCurrency, setInputCurrency] = useState('');
  const [outputCurrency, setOutputCurrency] = useState('');
  const [amount, setAmount] = useState('');
  const [date, setDate] = useState('');
  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);

  const handleConvert = async () => {
    try {
      setError(null); // Сброс ошибки перед новым запросом
      const response = await axios.post('/api/calculate', {
        inputCur: inputCurrency,
        outputCur: outputCurrency,
        date: date,
        sumCur: amount
      });
      setResult(response.data);
    } catch (error) {
      setError('Произошла ошибка при отправке запроса.');
      console.error("There was an error!", error);
    }
  };

  return (
    <div className="app">
      <div className="container">
        <h1>Конвертация валют</h1>
        <div className="form-group">
          <label htmlFor="inputCurrency">Из валюты:</label>
          <select
            id="inputCurrency"
            value={inputCurrency}
            onChange={(e) => setInputCurrency(e.target.value)}
          >
            <option value="">Выберите валюту</option>
            {currencies.map((currency) => (
              <option key={currency.code} value={currency.code}>
                {currency.name}
              </option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="outputCurrency">В валюту:</label>
          <select
            id="outputCurrency"
            value={outputCurrency}
            onChange={(e) => setOutputCurrency(e.target.value)}
          >
            <option value="">Выберите валюту</option>
            {currencies.map((currency) => (
              <option key={currency.code} value={currency.code}>
                {currency.name}
              </option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="amount">Сумма:</label>
          <input
            id="amount"
            type="number"
            value={amount}
            onChange={(e) => setAmount(Math.max(0, e.target.value))}
          />
        </div>
        <div className="form-group">
          <label htmlFor="date">Дата:</label>
          <input
            id="date"
            type="date"
            value={date}
            onChange={(e) => setDate(e.target.value)}
          />
        </div>
        <button onClick={handleConvert}>Посчитать</button>
        {result && (
          <div className="result">
            <h2>Результат:</h2>
            <p>{result}</p>
          </div>
        )}
        {error && (
          <div className="error">
            <h2>Ошибка:</h2>
            <p>{error}</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default App;