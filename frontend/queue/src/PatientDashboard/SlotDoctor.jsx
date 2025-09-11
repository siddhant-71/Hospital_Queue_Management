import React, { useState } from "react";
import "./SlotDoctor.css";
import axios from "axios";

const SlotDoctor = ({ setsearched, setslot, slots, individual }) => {
  const [appId, setappId] = useState();

  const dates = [
    "1 July", "2 July", "3 July",
    "4 July", "5 July", "6 July",
    "7 July",
  ];

  const isBooked = (slotNum) => false;

  const bookSlot = (slotNumber) => {
    bookThisSlot(slotNumber);
  };

  // ✅ Load Razorpay checkout script
  const loadRazorpayScript = () => {
    return new Promise((resolve) => {
      if (window.Razorpay) {
        resolve(true);
        return;
      }
      const script = document.createElement("script");
      script.src = "https://checkout.razorpay.com/v1/checkout.js";
      script.onload = () => resolve(true);
      script.onerror = () => resolve(false);
      document.body.appendChild(script);
    });
  };

  const PayNow = async () => {
    const isScriptLoaded = await loadRazorpayScript();
    if (!isScriptLoaded) {
      alert("Failed to load Razorpay script");
      return;
    }

    // Step 1: Initiate payment (get Razorpay orderId from backend)
    const response = await axios.post(
      `http://localhost:8080/payment/initiate/${appId}`
    );

    const paymentData = response.data;

    // Step 2: Open Razorpay checkout
    const options = {
      key: "rzp_test_YmTwJC615FKYFA", // 🔑 your Razorpay key
      amount: paymentData.amount,     // from backend
      currency: "INR",
      name: "Hospital Queue",
      description: "Doctor Slot Booking",
      order_id: paymentData.razorpayOrderId, // 🔑 from backend
      handler: async function (response) {
        // Step 3: Verify payment signature
        const payload = {
          razorpayPaymentId: response.razorpay_payment_id,
          razorpayOrderId: response.razorpay_order_id,
          razorpaySignature: response.razorpay_signature,
          paymentReference: paymentData.paymentReference,
        };

        const verifyResp = await axios.post(
          "http://localhost:8080/payment/verify",
          payload
        );

        if (verifyResp.data === true) {
          // Step 4: Complete booking
          await axios.post(
            `http://localhost:8080/payment/complete/${appId}`,
            {
              status: "success",
              paymentId: response.razorpay_payment_id,
              orderId: response.razorpay_order_id,
            }
          );
          alert("Payment successful 🎉 Slot booked!");
        } else {
          alert("Payment verification failed ❌");
        }
      },
      prefill: {
        name: "Test User",
        email: "siddhantd711@gmail.com",
        contact: "9028673711",
      },
      theme: {
        color: "#3399cc",
      },
    };

    const rzp = new window.Razorpay(options);
    rzp.open();
  };

  // Booking slot API
  const bookThisSlot = async (slotNumber) => {
    try {
      const resp = await axios.post(
        `http://localhost:8080/appointment/book/${slotNumber}`,
        {
          patientId: 3,
          doctorId: individual.id,
        }
      );
      setappId(resp.data.appointmentId);
      PayNow();
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className="slotdoctor-container">
      <div className="slotdoctor-wrapper">
        <table className="slotdoctor-table">
          <tbody>
            {dates.map((date, row) => (
              <tr key={date}>
                <td className="slotdoctor-date">{date}</td>
                {Array.from({ length: 7 }).map((_, col) => {
                  const slotNumber = row * 7 + col + 1;
                  return (
                    <td
                      key={`slot-${slotNumber}`}
                      className={
                        isBooked(slotNumber) ? "booked" : "slotdoctor-cell"
                      }
                    >
                      <button
                        disabled={isBooked(slotNumber)}
                        className="slotdoctor-btn"
                        onClick={() => bookSlot(slotNumber)}
                      >
                        {slotNumber}
                      </button>
                    </td>
                  );
                })}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default SlotDoctor;
