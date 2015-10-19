def multiples(x:Int): String ={
  val result = ""
  if ((x % 3 == 0) && (x % 5 == 0)) result.concat("FizzBuzz")
  else if (x % 3 == 0) result.concat("Fizz")
  else if (x % 5 == 0) result.concat("Buzz")
  else result.concat(x.toString)

}

for(i <- 1 to 100) println(multiples(i))
