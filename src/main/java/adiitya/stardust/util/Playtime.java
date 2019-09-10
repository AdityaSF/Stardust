package adiitya.stardust.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Playtime {

	private final int hours;
	private final int minutes;
	private final int seconds;

	private Playtime(int hours, int minutes, int seconds) {

		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	@Override
	public String toString() {
		return IntStream.of(hours, minutes, seconds)
				.mapToObj(String::valueOf)
				.map(s -> String.format("%2s", s).replace(" ", "0"))
				.collect(Collectors.joining(":"))
				.replaceAll("^(0:)+", "");
	}

	public static Playtime fromTicks(long ticks) {

		AtomicLong ms = new AtomicLong(ticks / 10000L);
		int hours = removeTime(ms, TimeUnit.HOURS);
		int minutes = removeTime(ms, TimeUnit.MINUTES);
		int seconds = removeTime(ms, TimeUnit.SECONDS);

		return new Playtime(hours, minutes, seconds);
	}

	private static int removeTime(AtomicLong base, TimeUnit unit) {

		long removed = unit.convert(base.get(), TimeUnit.MILLISECONDS);
		base.set(base.get() - unit.toMillis(removed));

		return (int) removed;
	}
}
