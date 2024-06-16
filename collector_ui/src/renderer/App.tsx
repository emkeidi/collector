import React from "react";
import { useDisclosure } from "@mantine/hooks";
import { AppShell, Burger, Group, Skeleton, ScrollArea } from "@mantine/core";
import "@mantine/core/styles.css";
import Logo from "../../public/icons/collector_no_background_mock.png";
import { createTheme, MantineProvider } from "@mantine/core";
import * as styles from "./App.module.css";

const theme = createTheme({
	autoContrast: true,
	luminanceThreshold: 0.4,
});

const App: React.FC = () => {
	const [opened, { toggle }] = useDisclosure();

	return (
		<MantineProvider theme={theme} defaultColorScheme="dark">
			<AppShell
				header={{ height: 60 }}
				footer={{ height: 60 }}
				navbar={{ width: 300, breakpoint: "sm", collapsed: { mobile: !opened } }}
				aside={{ width: 300, breakpoint: "md", collapsed: { desktop: false, mobile: true } }}
				padding="md"
				className={styles.appShell}
			>
				<AppShell.Header>
					<Group h="100%" px="md">
						<Burger opened={opened} onClick={toggle} hiddenFrom="sm" size="sm" />
						<img src={Logo} alt="Collector logo" style={{ height: 30 }} /> <span className={styles.navTitle}>Collector</span>
					</Group>
				</AppShell.Header>
				<AppShell.Navbar p="md">
					<AppShell.Section>Navbar header</AppShell.Section>
					<AppShell.Section grow my="md" component={ScrollArea}>
						60 links in a scrollable section
						{Array(60)
							.fill(0)
							.map((_, index) => (
								<Skeleton key={index} h={28} mt="sm" animate={false} />
							))}
					</AppShell.Section>
					<AppShell.Section>Navbar footer – always at the bottom</AppShell.Section>
				</AppShell.Navbar>
				<AppShell.Main>Main</AppShell.Main>
				<AppShell.Aside p="md">Aside</AppShell.Aside>
				<AppShell.Footer p="md">Collecting with you since 2024</AppShell.Footer>
			</AppShell>
		</MantineProvider>
	);
};

export default App;
