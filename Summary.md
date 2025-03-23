Time Spent: Around 26 hours spent on research and thinking, not all of it was coding.

For me, the hardest part was the frontend, as it’s very easy to make small mistakes that are hard to notice. On the FlightView page, for table page switching, I used some example code that I had written for another project (found on my GitHub:
https://github.com/kannik-k/Read-It-Later-frontend/blob/main/src/views/HomeView.vue).

When it came to frontend syntax errors, the most effective method was to describe the problem to ChatGPT and ask for help in finding the bug. However, I’ve learned to be critical of this approach since it doesn’t always catch everything or solve it perfectly.

The most help I got came from the course material for the web application project (instructor’s videos and slide decks) and from working with my team on the project itself (
https://github.com/kannik-k/ti0302-2024-backend
and
https://github.com/kannik-k/Read-It-Later-frontend).

Unresolved Issues Due to Time Constraints:

Layovers Support: I planned to implement this by collecting all destinations into an array and having a dictionary where each key represents a departure airport, and its value is an empty list. The goal is to add destinations to each respective list that can be reached from the given departure airport. The function would then return keys where the list contains the desired destination. This is definitely not the most efficient solution, but it’s a starting point for further development.

Seat Display: This issue seems to be related more to my JavaScript knowledge rather than the backend side, as everything seems to be working fine on that end. I have a temporary solution in place, but it’s not ideal, and I still need to do more research and Googling to get it working properly.

Sorting Issue: The sort order is off. The departure time is being handled incorrectly—likely due to the date being included. I’ll need to remove the date and sort by the actual date in ascending order.

Task Description: I didn’t encounter any particular problems or questions with the task description; it was clear.