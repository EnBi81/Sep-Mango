package utils;

public class PersonCollisionException extends CollisionException
{
  public PersonCollisionException()
  {
    this("Collision happened in a person's schedule");

  }
  public PersonCollisionException(String message)
  {
    super(message);
  }
}
