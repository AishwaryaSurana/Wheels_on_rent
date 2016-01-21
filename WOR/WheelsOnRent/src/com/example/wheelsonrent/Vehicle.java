package com.example.wheelsonrent;

import java.io.Serializable;

public class Vehicle implements Serializable{
	
		int wheeler,seater,ownerId,vehicleId;
		String type,state,city,name,reg_no,driver,availability,location;
		 

		double rent_daily,rent_per_km;
		
	                        public Vehicle()
	                        {
	                        
	                        }
	                        
	                        
		 
		 public Vehicle(int wheeler, int seater, int ownerId, String type,
				String state, String city, String name, String reg_no,
				String driver,String availability, double rent_daily, double rent_per_km,String location) 
		 {
			super();
			this.wheeler = wheeler;
			this.seater = seater;
			this.ownerId = ownerId;
			this.type = type;
			this.state = state;
			this.city = city;
			this.name = name;
			this.reg_no = reg_no;
			this.driver = driver;
			this.rent_daily = rent_daily;
			this.rent_per_km = rent_per_km;
			this.availability=availability;
	                                              this.location=location;
		}

		 public String getAvailability() {
				return availability;
			}


			public void setAvailability(String availability) {
				this.availability = availability;
			}

		public int getWheeler() {
			return wheeler;
		}


		public void setWheeler(int wheeler) {
			this.wheeler = wheeler;
		}


		public int getSeater() {
			return seater;
		}


		public void setSeater(int seater) {
			this.seater = seater;
		}


		public int getOwnerId() {
			return ownerId;
		}


		public void setOwnerId(int ownerId) {
			this.ownerId = ownerId;
		}


		public String getType() {
			return type;
		}


		public void setType(String type) {
			this.type = type;
		}


		public String getState() {
			return state;
		}


		public void setState(String state) {
			this.state = state;
		}


		public String getCity() {
			return city;
		}


		public void setCity(String city) {
			this.city = city;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getReg_no() {
			return reg_no;
		}


		public void setReg_no(String reg_no) {
			this.reg_no = reg_no;
		}


		public String getDriver() {
			return driver;
		}


		public void setDriver(String driver) {
			this.driver = driver;
		}


		public double getRent_daily() {
			return rent_daily;
		}


		public void setRent_daily(double rent_daily) {
			this.rent_daily = rent_daily;
		}


		public double getRent_per_km() {
			return rent_per_km;
		}


		public void setRent_per_km(double rent_per_km) {
			this.rent_per_km = rent_per_km;
		}

	                        public String getLocation() {
	                            return location;
	                        }

	                        public void setLocation(String location) {
	                            this.location = location;
	                        }


//		@Override
//		public String toString() {
//			return "{\"wheeler:\""+wheeler+","+"\"seater:\""+seater
//					+","+"\"owner_id\""+ownerId+","+"\"type:\""+type+","
//					+"\"state\""+state+","+"\"city:\""+city+","+"\"name\""+name+","
//					+"\"reg_no\""+reg_no+","+"\"driver\""+driver+","+"\"rent_daily\""+rent_daily
//					+","+"\"rent_per_km\""+rent_per_km+","+"\"location\""+location+"}";
//		}
		 

	}

