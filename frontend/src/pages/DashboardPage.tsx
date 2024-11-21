import React from "react";

const DashboardPage: React.FC = () => {
  return (
    <div className="mt-6 grid grid-cols-1 md:grid-cols-3 gap-4">
      <div className="p-6 bg-green-300 text-gray-800 shadow rounded-lg">
        <h2 className="text-xl font-bold">Health Tips</h2>
        <p className="mt-2">Stay active, hydrated, and eat balanced meals.</p>
      </div>
      <div className="p-6 bg-white shadow rounded-lg">
        <h2 className="text-xl font-bold text-gray-800">
          Upcoming Appointments
        </h2>
        <p className="mt-2 text-gray-600">No upcoming appointments.</p>
      </div>
      <div className="p-6 bg-purple-200 text-gray-800 shadow rounded-lg">
        <h2 className="text-xl font-bold">Recent Consultations</h2>
        <p className="mt-2">Last consultation: Nov 18, 2024</p>
      </div>
    </div>
  );
};

export default DashboardPage;
