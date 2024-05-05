"use client";
// pages/RequestForm.js

import { useState } from 'react';

export default function RequestForm() {
  const [formData, setFormData] = useState({
    from: '',
    toTeam: '',
    resourceUrl: '',
    reason: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Here you can handle the form submission, e.g., send the data to a backend API
    console.log(formData);
    // Reset the form after submission
    setFormData({
      from: '',
      toTeam: '',
      resourceUrl: '',
      reason: ''
    });
  };

  return (
    <div>
      <h1>Permission Request Form</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="from">From:</label>
          <input
            type="text"
            id="from"
            name="from"
            value={formData.from}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="toTeam">To Team:</label>
          <input
            type="text"
            id="toTeam"
            name="toTeam"
            value={formData.toTeam}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="resourceUrl">Resource URL:</label>
          <input
            type="text"
            id="resourceUrl"
            name="resourceUrl"
            value={formData.resourceUrl}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="reason">Reason:</label>
          <textarea
            id="reason"
            name="reason"
            value={formData.reason}
            onChange={handleChange}
          ></textarea>
        </div>
        <button type="submit">Submit Request</button>
      </form>
    </div>
  );
}