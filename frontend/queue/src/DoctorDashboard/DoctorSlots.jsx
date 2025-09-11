import React, { useEffect } from "react";
import CellComponent from "./CellComponent";

const DoctorSlots = () => {
    const slots=localStorage.getItem('slots');
    const arr = slots.split(",").map(Number);  
    const cells=[];
    for(let i=0;i<56;i++){
      let classN="Cell";
      if(arr[i]===0){
        classN='change';
      }
      cells.push(<CellComponent classNa={classN} i={i} no={arr[i]} key={i}/>)
    }
  return (
    <>
    <h1>Slots</h1>
    <div className="Tables">
      {cells}
    </div>
    </>
  );
};

export default DoctorSlots;
