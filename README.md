# Suggestion-score challenge
Requirements:
* java 8
* maven

To start the application:
1) Clone the repository
2) Run `mvn spring-boot:run` in the root folder
### API
Request GET http://localhost:8080/estimate?keyword=iphone

Response body:
```json
{
    "keyword": "iphone",
    "score": 93
}
```

### Explanation
The logic behind my approach is calling the service with the keyword starting from the first letter,
then adding the next... until the full keyword is sent.

Example: keyword = beer
1) call query - b
2) call query - be
3) call query - bee
4) final call query = beer

For each call a list of suggestions is received. By my observation it's size is usually 10 or 0. 
My algorithm is really basic, it counts how many times the keyword appears in each list (a number from 0 to 10), sums all the scores together
and calculates the percentage depending on the keyword length: 100/keyword.length * overallScore.


By playing around the amazon completion field it does seem that the return keywords are ordered more or less by the popularity.
One hint is that the list order is not changing for the same input.


The outcome score is probably not really precise because of the primitive score calculation used.
For example, it could be improved by adding higher weight of the score for the matched suggestions
received with the least parts of the keyword.
