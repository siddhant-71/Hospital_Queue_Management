import React, { useState } from "react";
import "./SlotDoctor.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";
const BackendUrl=import.meta.env.VITE_BACKEND_URL

const SlotDoctor = ({ setsearched, setslot, slots, individual }) => {
  const [appId, setappId] = useState();
  const navigate=useNavigate();

  const dates = [
    "1 July", "2 July", "3 July",
    "4 July", "5 July", "6 July",
    "7 July",
  ];

  const isBooked = (slotNum) => false;

  const bookSlot = (slotNumber) => {
    bookThisSlot(slotNumber);
  };
  const bookThisSlot = async (slotNumber) => {
    try {
      const resp = await axios.post(
        `${BackendUrl}/appointment/book/${slotNumber}`,
        {
          patientId: 3,
          doctorId: individual.id,
        }
      );
      setappId(resp.data.appointmentId);
      navigate('/Pay',{state:{data:resp.data}});
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
