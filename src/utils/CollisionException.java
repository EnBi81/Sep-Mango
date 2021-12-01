package utils;

import java.time.LocalDateTime;

public class CollisionException extends RuntimeException
{
  LocalDateTime collisionFrom;
  LocalDateTime collisionTo;

  public CollisionException()
  {

  }
  public CollisionException(String message)
  {
    super(message);
  }
  public CollisionException(LocalDateTime from, LocalDateTime to)
  {
    collisionFrom = from;
    collisionTo = to;
  }

  public LocalDateTime getCollisionFrom()
  {
    return collisionFrom;
  }

  public LocalDateTime getCollisionTo()
  {
    return collisionTo;
  }
}
