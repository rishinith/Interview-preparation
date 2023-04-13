/**
 A company has hired N interns to each join one of N different teams. Each
intern has ranked their preferences for which teams they wish to join, and
each team has ranked their preferences for which interns they prefer.
Given these preferences, assign 1 intern to each team. These assignments
should be "stable," meaning that there is no unmatched pair of an intern and a
team such that both that intern and that team would prefer they be matched
with each other.
In the case there are multiple valid stable matchings, the solution that is
most optimal for the interns should be chosen (i.e. every intern should be
matched with the best team possible for them).
Your function should take in 2 2-dimensional lists, one for interns and
one for teams. Each inner list represents a single intern or team's preferences,
ranked from most preferable to least preferable. These lists will always be
of length N, with integers as elements. Each of these integers corresponds
to the index of the team/intern being ranked. Your function should return a
2-dimensional list of matchings in no particular order. Each matching should
be in the format [internIndex, teamIndex].
Sample Input
interns = [
[0, 1, 2],
[1, 0, 2],
[1, 2, 0]
]
teams = [
[2, 1, 0],
[1, 2, 0],
[0, 2, 1]
]
Sample Output
// This is the most optimal solution for interns
[
[0, 0],
[1, 1],
[2, 2]
]
// This is also a stable matching, but it is suboptimal for the interns
// because interns 0 and 2 could have been given better team matchings
[
[2, 0],
[1, 1],
[0, 2]
]

 */

/**
 * 1) Start choosing the first priority team for each intern
 * 2) In case of conflict For eg: intern want to choose already allocated team because of its priority order, 
 * check team's intern priority list to alot the team to the previous or current intern.
 * 
 * 
 * 
 */

import java.util.*;

class Program {
  public int[][] stableInternships(int[][] interns, int[][] teams) {

    int n=interns.length;
    //Each index is the intern and the value is the index of the team priority list. 
    // Default 0, means first priority
    int[] internChoiceIndex=new int[n];

    //map: key:team value:intern. Final answer
    Map<Integer,Integer> teamInternMapping=new HashMap<>();

    //List index:Team , Map- key:intern value:ranking
    //This to use when in conflict, two interns want to choose same team.
    //here Team can decide what intern to choose on basis of their priority
    List<Map<Integer,Integer>> internsRankingByTeam=new ArrayList<>();
    for(int[] team:teams){
      Map<Integer,Integer> internsRanking=new HashMap<>();
      for(int i=0;i<team.length;i++){
        internsRanking.put(team[i],i);
      }
      internsRankingByTeam.add(internsRanking);
    }

    Stack<Integer> freeInterns=new Stack<>();
    for(int i=0;i<n;i++){
      freeInterns.add(i);
    }

    while(!freeInterns.isEmpty()){
      int currentIntern=freeInterns.pop();
      int currentTeamIndex=internChoiceIndex[currentIntern];
      int team=interns[currentIntern][currentTeamIndex];
      //increment the index to choose next team for next iteration if this team is not optimal
      internChoiceIndex[currentIntern]++;

      //This team is not allocated to any other intern
      if(!teamInternMapping.containsKey(team)){
        teamInternMapping.put(team,currentIntern);
        continue;
      }

      //In case of team conflicts fetch the ranking of given interns for the team
      int previousInternForTeam=teamInternMapping.get(team);
      int previousInternRanking=internsRankingByTeam.get(team).get(previousInternForTeam);
      int currentInternRanking=internsRankingByTeam.get(team).get(currentIntern);

      if(previousInternRanking<=currentInternRanking){
        //push current inter again for next team choose by preference
        freeInterns.push(currentIntern);
      }else{
        teamInternMapping.put(team,currentIntern);
        //push previous intern again for next team choose by preference
        freeInterns.push(previousInternForTeam);
      }
      
    }

    var result= new int[n][2];
    for(var entry:teamInternMapping.entrySet()){
        result[entry.getValue()][0]=entry.getValue();
        result[entry.getValue()][1]=entry.getKey(); 
    }
    
    
    
    return result;
  }
}
