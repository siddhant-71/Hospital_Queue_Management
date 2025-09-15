import axios from 'axios';
import React, { useEffect } from 'react'
import { useLocation, useNavigate } from 'react-router-dom';
const Key = import.meta.env.VITE_PAYMENT_KEY;
const BackendUrl=import.meta.env.VITE_BACKEND_URL

const Payment = () => {
    const location=useLocation();
    const navigate=useNavigate();
    const {data}=location.state;
    useEffect(() => {
      localStorage.setItem('apd',data.appointmentId)
    }, [])
    
    let orderIdd="";
    let i=data.slot;
    let day='';
    if(i/8>=0 && i/8<1)day="SUNDAY"
    else if(i/8>=1 && i/8<2)day="MONDAY"
    else if(i/8>=2 && i/8<3)day="TUESDAY"
    else if(i/8>=3 && i/8<4)day="WEDNESDAY"
    else if(i/8>=4 && i/8<5)day="THURSDAY"
    else if(i/8>=5 && i/8<6)day="FRIDAY"
    else if(i/8>=6 && i/8<8)day="SATURDAY"
    let time='';
    if(i%8==0)time='9-10 am'
    else if(i%8==1)time='10-11 am'
    else if(i%8==2)time='11-12 noon'
    else if(i%8==3)time='12-1 pm'
    else if(i%8==4)time='1-2 pm'
    else if(i%8==5)time='2-3 pm'
    else if(i%8==6)time='3-4 pm'
    else if(i%8==7)time='4-5 pm'

    function loadRazorpayScript(src="https://checkout.razorpay.com/v1/checkout.js"){
        return new Promise((resolve)=>{
            if(document.querySelector(`script[src="${src}"]`)){
                resolve(true);
                return;
            }
            const script=document.createElement("script");
            script.src=src;
            script.onload=()=>resolve(true);
            script.onerror=()=>resolve(false);
            document.body.appendChild(script);
        });
    }
    async function payfee() {
        const response1=await axios.post(`${BackendUrl}/payment/initiate/${data.appointmentId}`);
        console.log(response1.data)
        orderIdd=response1.data.razorpayOrderId;
        const loaded=await loadRazorpayScript();
        if(!loaded){
            alert("Razorpay SDK failed to load , CHECK CONNECTION")
            return ;
        }
         const options = {
            key: `${Key}`, // Replace with your Key ID from Razorpay Dashboard
            amount: "50000", // Amount in paise (50000 = ₹500)
            currency: "INR",
            name: "SIDDHANT",
            description: "Test Transaction",
            order_id: orderIdd, // Pass the order_id from backend
            handler:async function (response) {
                console.log("Payment ID: " + response.razorpay_payment_id);
                console.log("Order ID: " + response.razorpay_order_id);
                console.log("Signature: " + response.razorpay_signature);
                console.log(localStorage.getItem('apd'))
                const comp=await axios.post(`${BackendUrl}/payment/complete/${localStorage.getItem('apd')}`,
                    {
                        "RazorpayOrderId":response.razorpay_order_id,
                        "RazorpayPaymentID":response.razorpay_payment_id,
                        "RazorpaySignature":response.razorpay_signature
                    }
                )
                console.log(comp.data);
                navigate('/Patient')
            },
            prefill: {
                name: localStorage.getItem("name"),
                email: localStorage.getItem("email"),
                contact: localStorage.getItem("phoneNo"),
            },
            notes: {
                address: "Razorpay Corporate Office",
            },
            theme: {
                color: "#3399cc",
            },
        };
        const rzp1=new window.Razorpay(options);

        
            rzp1.on("payment.failed", function (response) {
            console.log(response.data);
            });
            rzp1.open();
    }

  return (
    <>
    <div>Doctor Name - {data.doctorName}</div>
    <div>Status - {data.status}</div>
    <div>{day} {time}</div>
    <hr />
    <button onClick={payfee}>PAY BOOKING FEE</button>
    </>
  )
}

export default Payment