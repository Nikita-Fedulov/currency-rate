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
      setError(null); // Reset any previous errors
      const response = await axios.post('/api/calculate', {
        inputCur: inputCurrency,
        outputCur: outputCurrency,
        date: date,
        sumCur: amount
      });

      // Formatting the result
      const formattedDate = new Date(date).toLocaleDateString('ru-RU', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      });

      const formattedResult = `${amount} ${inputCurrency} = ${response.data} ${outputCurrency} (по курсу на ${formattedDate})`;

      setResult(formattedResult);
    } catch (error) {
      setError('Произошла ошибка при отправке запроса.');
      console.error("There was an error!", error);
    }
  };

  return (
    <div className="app">
      <div className="container">
        <h1>Калькулятор валют</h1>
        <div className="form-group currency-group">
          <label htmlFor="amount" className="currency-label">Сумма:</label>
          <input
            id="amount"
            type="number"
            value={amount}
            onChange={(e) => setAmount(Math.max(0, e.target.value))}
            className="currency-input"
          />
          <label htmlFor="inputCurrency" className="currency-label">Перевод из валюты:</label>
          <input
            id="inputCurrency"
            value={inputCurrency}
            onChange={(e) => setInputCurrency(e.target.value)}
            list="currency-options"
            className="currency-input"
          />
          <datalist id="currency-options">
            {currencies.map((currency) => (
              <option key={currency.code} value={currency.code}>
                {currency.name}
              </option>
            ))}
          </datalist>
        </div>
        <div className="form-group">
          <label htmlFor="outputCurrency">Перевести в:</label>
          <input
            id="outputCurrency"
            value={outputCurrency}
            onChange={(e) => setOutputCurrency(e.target.value)}
            list="currency-options"
            className="currency-input"
          />
        </div>
        <div className="form-group">
          <label htmlFor="date">По курсу ЦБ за:</label>
          <input
            id="date"
            type="date"
            value={date}
            onChange={(e) => setDate(e.target.value)}
            className="currency-input"
          />
        </div>
        <button onClick={handleConvert}>Рассчитать</button>
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