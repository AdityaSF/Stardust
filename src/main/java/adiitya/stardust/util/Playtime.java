package adiitya.stardust.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Playtime {

	private final int hours;
	private final int minutes;
	private final int seconds;

	public Playtime(int hours, int minutes, int seconds) {

		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	/**
	 * Converts the instance to a string in the format {@code hh:mm:ss}. The
	 * values will be padded with zeros to a length of 2.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return IntStream.of(hours, minutes, seconds)
				.mapToObj(String::valueOf)
				.map(s -> String.format("%2s", s).replace(" ", "0"))
				.collect(Collectors.joining(":"))
				.replaceAll("^(0:)+", "");
	}

	/**
	 * Converts .NET DateTime ticks into a Playtime instance. A tick is 10,000 milliseconds.
	 *
	 * @param ticks the ticks
	 * @return the instance
	 */
	public static Playtime fromTicks(long ticks) {

		AtomicLong ms = new AtomicLong(ticks / 10000L);
		int hours = removeTime(ms, TimeUnit.HOURS);
		int minutes = removeTime(ms, TimeUnit.MINUTES);
		int seconds = removeTime(ms, TimeUnit.SECONDS);

		return new Playtime(hours, minutes, seconds);
	}

	/**
	 * Removes the specified type of time from the total, then returns how much was removed.
	 *
	 * @param base the base time
	 * @param unit the TimeUnit
	 * @return the amount of time removed
	 */
	private static int removeTime(AtomicLong base, TimeUnit unit) {

		long removed = unit.convert(base.get(), TimeUnit.MILLISECONDS);
		base.set(base.get() - unit.toMillis(removed));

		return (int) removed;
	}
}
