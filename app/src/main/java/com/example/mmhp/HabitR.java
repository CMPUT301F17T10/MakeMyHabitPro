package com.example.mmhp;

import java.util.ArrayList;

/**
 * Created by BBC on 2017/11/11.
 */

public class HabitR {

    private ArrayList<HabitEntity> habit;

    public void setHabit(ArrayList<HabitEntity> habit) {
        this.habit = habit;
    }

    public ArrayList<HabitEntity> getHabit() {
        return habit;
    }

    public static class HabitEntity {
        private String userId;
        private ArrayList<UserHabitEntity> userHabit;

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setUserHabit(ArrayList<UserHabitEntity> userHabit) {
            this.userHabit = userHabit;
        }

        public String getUserId() {
            return userId;
        }

        public ArrayList<UserHabitEntity> getUserHabit() {
            return userHabit;
        }

        public static class UserHabitEntity {
            private String title;
            private String reason;
            private String detail;
            private String type;
            private String startDate;
            private PlanEntity plan;

            public void setTitle(String title) {
                this.title = title;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public void setPlan(PlanEntity plan) {
                this.plan = plan;
            }

            public String getTitle() {
                return title;
            }

            public String getReason() {
                return reason;
            }

            public String getDetail() {
                return detail;
            }

            public String getType() {
                return type;
            }

            public String getStartDate() {
                return startDate;
            }

            public PlanEntity getPlan() {
                return plan;
            }

            public static class PlanEntity {
                private boolean Sun;
                private boolean Mon;
                private boolean Tue;
                private boolean Wen;
                private boolean Thu;
                private boolean Fri;
                private boolean Sat;

                public void setSun(boolean Sun) {
                    this.Sun = Sun;
                }

                public void setMon(boolean Mon) {
                    this.Mon = Mon;
                }

                public void setTue(boolean Tue) {
                    this.Tue = Tue;
                }

                public void setWen(boolean Wen) {
                    this.Wen = Wen;
                }

                public void setThu(boolean Thu) {
                    this.Thu = Thu;
                }

                public void setFri(boolean Fri) {
                    this.Fri = Fri;
                }

                public void setSat(boolean Sat) {
                    this.Sat = Sat;
                }

                public boolean getSun() {
                    return Sun;
                }

                public boolean getMon() {
                    return Mon;
                }

                public boolean getTue() {
                    return Tue;
                }

                public boolean getWen() {
                    return Wen;
                }

                public boolean getThu() {
                    return Thu;
                }

                public boolean getFri() {
                    return Fri;
                }

                public boolean getSat() {
                    return Sat;
                }
            }
        }
    }
}
