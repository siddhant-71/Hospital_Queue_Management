import React from 'react'

const DetailDoctor = ({individual}) => {
  return (
    <>
        <div>
            {individual.hospital.name}
        </div>
        <div className='detail'>
            <div>{individual.name}</div>
            <div>{individual.specialization}</div>
            <div>{individual.degrees}</div>
            <div>{individual.department}</div>
        </div>
    </>
  )
}

export default DetailDoctor